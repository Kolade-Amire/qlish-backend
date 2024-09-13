package com.qlish.qlish_api.service;

import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import com.qlish.qlish_api.constants.TestSubject;
import com.qlish.qlish_api.constants.english_enums.EnglishAttributes;
import com.qlish.qlish_api.constants.english_enums.EnglishQuestionClass;
import com.qlish.qlish_api.constants.english_enums.EnglishQuestionLevel;
import com.qlish.qlish_api.constants.english_enums.EnglishQuestionTopic;
import com.qlish.qlish_api.dto.EnglishQuestionDto;
import com.qlish.qlish_api.dto.EnglishTestDto;
import com.qlish.qlish_api.dto.TestSubmissionRequest;
import com.qlish.qlish_api.entity.*;
import com.qlish.qlish_api.exception.CustomDatabaseException;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.mapper.EnglishQuestionMapper;
import com.qlish.qlish_api.mapper.EnglishTestMapper;
import com.qlish.qlish_api.repository.EnglishTestRepository;
import com.qlish.qlish_api.util.EnglishTestFactory;
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
public class EnglishTestServiceImpl implements EnglishTestService {

    private static final Logger logger = LoggerFactory.getLogger(EnglishTestServiceImpl.class);


    private final EnglishTestRepository englishTestRepository;
    private final EnglishQuestionService englishQuestionService;

    @Override
    public List<EnglishTest> getAllUserTests(ObjectId userId) {
        return englishTestRepository.findAllUserTests(userId).orElseThrow(
                () -> new EntityNotFoundException("User has not taken any test.")
        );
    }

    @Override
    public EnglishTest getTestById(ObjectId id) {
        return englishTestRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Test not found.")
        );
    }

    @Override
    public EnglishTestDto getTestForView(ObjectId id) {
        var test = getTestById(id);
        return EnglishTestMapper.mapTestToDto(test);
    }


    @Override
    public ObjectId saveTest(EnglishTest test) {
        try {
            return englishTestRepository.save(test).get_id();
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
    public void deleteTest(ObjectId testId) {
        var test = getTestById(testId);
        englishTestRepository.delete(test);
    }


    @Override
    public ObjectId initiateNewTest(ObjectId userId, EnglishTestFactory testFactory) {


        var questions = getQuestionsList(testFactory);

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
                .questions(questions)
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
    public Page<EnglishQuestionDto> getTestQuestionsForView(ObjectId testId, Pageable pageable) {

        var test = getTestById(testId);

        var questions = test.getQuestions();

        // Perform pagination over the full list of questions
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), questions.size());

        var paginatedQuestions = questions.subList(start, end);

        // Convert to DTOs for frontend response
        List<EnglishQuestionDto> questionDtoList = EnglishQuestionMapper.mapQuestionListToDto(paginatedQuestions);

        // Return the paginated page of questions
        return new PageImpl<>(questionDtoList, pageable, questions.size());
    }

    //TODO
    @Override
    public ObjectId submitTest(List<TestSubmissionRequest> submission) {
        return null;
    }

    @Override
    public TestResult getResult(ObjectId id) {
        return null;
    }


}
