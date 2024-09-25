package com.qlish.qlish_api.service;

import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.dto.*;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.TestDetails;
import com.qlish.qlish_api.entity.TestEntity;
import com.qlish.qlish_api.entity.TestResult;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.exception.CustomDatabaseException;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.exception.TestResultException;
import com.qlish.qlish_api.exception.TestSubmissionException;
import com.qlish.qlish_api.factory.QuestionRepositoryFactory;
import com.qlish.qlish_api.factory.ResultCalculationFactory;
import com.qlish.qlish_api.mapper.QuestionMapper;
import com.qlish.qlish_api.mapper.TestMapper;
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

@Service
@RequiredArgsConstructor
@Transactional
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
    private final QuestionRepositoryFactory questionRepositoryFactory;

    private final ResultCalculationFactory resultCalculationFactory;

    private final TestRepository testRepository;

    //TODO: test status  implementation



    @Override
    public TestEntity getTestById(ObjectId id) {
            return testRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Test not found with ID: " + id)
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
        var subject = TestSubject.getSubjectByDisplayName(request.getTestSubject().toLowerCase());
        var repository = questionRepositoryFactory.getRepository(subject);
        var modifier = questionRepositoryFactory.getModifier(subject, request.getModifiers());

        List<? extends Question> questions = repository.getTestQuestions(modifier, request.getQuestionCount());

        TestDetails testDetails = TestDetails.builder()
                .userId(request.getUserId())
                .testSubject(subject)
                .startedAt(LocalDateTime.now())
                .totalQuestionCount(request.getQuestionCount())
                .isCompleted(false)
                .build();

        List<CompletedTestQuestionDto> questionsDto = QuestionMapper.mapQuestionListToSavedTestQuestionDto(questions);

        var newTest = TestEntity.builder()
                .testDetails(testDetails)
                .questions(questionsDto)
                .build();

        return saveTest(newTest);
    }


    @Override
    public void deleteTest(ObjectId testId) {
        var test = getTestById(testId);
        testRepository.delete(test);
    }

    @Override
    public void deleteAllUserTests(ObjectId userId) {
        //TODO
    }


    @Override
    public Page<TestQuestionDto> getTestQuestions(ObjectId testId, Pageable pageable) {

        var test = getTestById(testId);

        var questions = test.getQuestions();

        // Perform pagination over the full list of questions
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), questions.size());
        var paginatedQuestions = questions.subList(start, end);

        // Convert to DTOs for frontend response
        List<TestQuestionDto> questionDtoList = QuestionMapper.mapQuestionListToTestViewDto(paginatedQuestions);

        // Return the paginated page of questions
        return new PageImpl<>(questionDtoList, pageable, questions.size());
    }

    @Override
    public ObjectId submitTest(ObjectId id, TestSubmissionRequest request) {

        try {
            var test = getTestById(id);
            var answers = request.getAnswers();

            // Process each answer and map it to the corresponding question
            for (TestQuestionSubmissionRequest submission : answers) {
                // Find the testQuestion in the test
                var testQuestion = test.getQuestions().stream()
                        .filter(q -> q.get_id().equals(submission.getQuestionId()))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Question not found"));

                // Store the submission in the corresponding question of the test
                testQuestion.setSelectedOption(submission.getSelectedOption());
                testQuestion.setAnswerCorrect(submission.getSelectedOption().equals(testQuestion.getCorrectAnswer()));
            }

            // Mark the test as completed
            test.getTestDetails().setCompleted(true);

            saveTest(test);

            return test.get_id();
        }
        catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            throw new TestSubmissionException(AppConstants.TEST_SUBMISSION_ERROR);
        }

    }

    @Override
    public TestResult getTestResult(ObjectId id) {
        try {
            var test = getTestById(id);
            var strategy = resultCalculationFactory.getStrategy(test.getTestDetails().getTestSubject());

            return strategy.calculateResult(test.getQuestions());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new TestResultException(e.getMessage());
        }
    }


}
