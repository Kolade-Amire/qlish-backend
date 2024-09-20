package com.qlish.qlish_api.service;

import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import com.qlish.qlish_api.dto.*;
import com.qlish.qlish_api.entity.*;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.enums.english_enums.EnglishAttributes;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionClass;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionLevel;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionTopic;
import com.qlish.qlish_api.exception.CustomDatabaseException;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.factory.QuestionRepositoryFactory;
import com.qlish.qlish_api.mapper.QuestionMapper;
import com.qlish.qlish_api.mapper.TestMapper;
import com.qlish.qlish_api.repository.QuestionRepository;
import com.qlish.qlish_api.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
    private final Map<String, QuestionRepositoryFactory<? extends Question>> repositoryFactories;


    private final TestRepository testRepository;



    @Override
    public TestEntity getTestById(ObjectId id) {
        return testRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Test not found.")
        );
    }

    @Override
    public TestDto getTestForView(ObjectId id) {
        var test = getTestById(id);
        return TestMapper.mapTestToDto(test);
    }


    @Override
    public ObjectId saveTest(TestEntity test) {
        try {
            return testRepository.save(test).get_id();
        } catch (MongoWriteException e) {
            logger.error("Mongo write error: {}", e.getMessage());
            throw new CustomDatabaseException("Error writing to MongoDB: " + e.getMessage(), e);
        } catch (MongoTimeoutException e) {
            logger.error("Mongo timeout error: {}", e.getMessage());
            throw new CustomDatabaseException("MongoDB connection timeout: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            logger.error("Data access error: {}", e.getMessage());
            throw new CustomDatabaseException("Data access error: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            throw new CustomDatabaseException("Unexpected error occurred while saving entity: " + e.getMessage(), e);
        }
    }

    @Override
    public ObjectId createTest(TestRequest request) {
        var subject = request.getTestSubject();
        var factory = repositoryFactories.get(subject);
        var repository = factory.getRepository(TestSubject.getSubjectByDisplayName(subject));

        var questions = repository.getTestQuestions();
    }

    @Override
    public Page<QuestionViewDto> getTestQuestions(ObjectId id) {
        return null;
    }


    @Override
    public void deleteTest(ObjectId testId) {
        var test = getTestById(testId);
        englishTestRepository.delete(test);
    }

    @Override
    public void deleteAllUserTests(ObjectId userId) {

    }


    @Override
    public ObjectId initiateNewTest(ObjectId userId, EnglishTestFactory testFactory) {


        List<EnglishQuestionEntity> questions = getQuestionsList(testFactory);

        var questionSubmissionList = QuestionMapper.mapQuestionListToTestDto(questions);

        var newTestDetails = TestDetails.builder()
                .userId(userId)
                .testSubject(TestSubject.ENGLISH)
                .startedAt(LocalDateTime.now())
                .totalQuestionCount(testFactory.getQuestionCount())
                .isCompleted(false)
                .build();

        var newEnglishTest = EnglishTest.builder()
                .testDetails(newTestDetails)
                .modifier(testFactory.getAllModifiers())
                .questions(questionSubmissionList)
                .build();


        return saveTest(newEnglishTest);


    }


    @Override
    public List<EnglishQuestionEntity> getQuestionsList(EnglishTestFactory testFactory) {

        var questionClass = testFactory.getModifier(EnglishAttributes.CLASS.getAttributeName());
        var questionLevel = testFactory.getModifier(EnglishAttributes.LEVEL.getAttributeName());
        var questionTopic = testFactory.getModifier(EnglishAttributes.TOPIC.getAttributeName());

        return englishQuestionService.getEnglishQuestions(
                EnglishQuestionLevel.fromLevelName(questionLevel),
                EnglishQuestionClass.fromClassName(questionClass),
                EnglishQuestionTopic.fromTopicName(questionTopic),
                testFactory.getQuestionCount());
    }


    @Override
    public Page<QuestionViewDto> getTestQuestionsForView(ObjectId testId, Pageable pageable) {

        var test = getTestById(testId);

        var questions = test.getQuestions();

        // Perform pagination over the full list of questions
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), questions.size());

        var paginatedQuestions = questions.subList(start, end);

        // Convert to DTOs for frontend response
        List<QuestionViewDto> questionDtoList = QuestionMapper.mapQuestionListToViewDto(paginatedQuestions);

        // Return the paginated page of questions
        return new PageImpl<>(questionDtoList, pageable, questions.size());
    }

    //TODO: test status state implementation
    @Override
    public ObjectId submitTest(TestSubmissionRequest request) {
        var test = getTestById(request.getId());
        var answers = request.getQuestionSubmissionRequests();

        // Process each answer and map it to the corresponding question
        for (QuestionSubmissionRequest submission : answers) {
            // Find the testQuestion in the test
            var testQuestion = test.getQuestions().stream()
                    .filter(q -> q.get_id().equals(submission.getQuestionId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Question not found"));

            // Store the submission in the corresponding testQuestion of the test
            testQuestion.setSelectedOption(submission.getSelectedOption());
            testQuestion.setAnswerCorrect(submission.getSelectedOption().equals(testQuestion.getCorrectAnswer()));
        }

        // Mark the test as completed
        test.getTestDetails().setCompleted(true);

        saveTest(test);

        return test.get_id();

    }

    @Override
    public TestResult getTestResult(ObjectId id) {
        return null;
    }

    @Override
    public TestResult getResult(ObjectId id) {
        return  null;
    }


}
