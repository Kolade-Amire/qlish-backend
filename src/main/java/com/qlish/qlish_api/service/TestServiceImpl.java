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
import com.qlish.qlish_api.dto.EnglishTestRequest;
import com.qlish.qlish_api.dto.TestSubmissionRequest;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.EnglishTest;
import com.qlish.qlish_api.entity.TestEntity;
import com.qlish.qlish_api.entity.TestResult;
import com.qlish.qlish_api.exception.CustomDatabaseException;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.mapper.EnglishQuestionMapper;
import com.qlish.qlish_api.repository.EnglishTestRepository;
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


    private final EnglishTestRepository englishTestRepository;
    private final EnglishQuestionService englishQuestionService;

    @Override
    public List<EnglishTest> findEnglishTestsByUserId(ObjectId userId) {
        return englishTestRepository.findAllByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("User has not taken any test.")
        );
    }

    @Override
    public TestEntity findTestById(ObjectId id) {
        return testRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Test not found.")
        );
    }

    @Override
    public ObjectId saveTest(TestEntity testEntity) {
        try {
            return testRepository.save(testEntity).get_id();
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
    public void delete(TestEntity testEntity) {
        testRepository.delete(testEntity);
    }

    @Override
    public EnglishTestDto startNewEnglishTest(EnglishTestRequest englishTestRequest, Pageable pageable) {
        var testModifier = englishTestRequest.getTestModifier();
        var questionClass = testModifier.getModifier(EnglishAttributes.CLASS.getAttributeName());
        var questionLevel = testModifier.getModifier(EnglishAttributes.LEVEL.getAttributeName());
        var questionTopic = testModifier.getModifier(EnglishAttributes.TOPIC.getAttributeName());

       var questions =  englishQuestionService.getEnglishQuestions(
                pageable,
               EnglishQuestionLevel.fromLevelName(questionLevel),
               EnglishQuestionClass.fromClassName(questionClass),
               EnglishQuestionTopic.fromTopicName(questionTopic),
               englishTestRequest.getQuestionCount());

       var newTest = TestEntity.builder()
               .userId(englishTestRequest.getUserId())
               .testSubject(TestSubject.ENGLISH.getSubjectName())
               .testModifier(englishTestRequest.getTestModifier())
               .startedAt(LocalDateTime.now())
               .totalQuestionCount(englishTestRequest.getQuestionCount())
               .isCompleted(false)
               .build();

       var savedTestId = saveTest(newTest);

       var questionDtoList = EnglishQuestionMapper.mapQuestionListToDto(questions);
       var questionPage = new PageImpl<>(questionDtoList, pageable, questionDtoList.size());




       return EnglishTestDto.builder()
               .id(savedTestId)
               .userId(englishTestRequest.getUserId())
               .testSubject(TestSubject.ENGLISH.getSubjectName())
               .totalQuestionCount(englishTestRequest.getQuestionCount())
               .questions(questionPage)
               .build();

    }

    public Page<EnglishQuestionDto> getPagedQuestionsForTest(ObjectId testId, Pageable pageable) {
        // Retrieve the test by ID
        EnglishTest test = testRe(testId)
                .orElseThrow(() -> new EntityNotFoundException("Test not found"));

        // Get the full list of questions stored in the test entity
        List<EnglishQuestionEntity> allQuestions = test.getQuestions();

        // Perform in-memory pagination over the full list of questions
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), allQuestions.size());

        List<EnglishQuestionEntity> paginatedQuestions = allQuestions.subList(start, end);

        // Convert to DTOs for frontend response
        List<EnglishQuestionDto> questionDtos = EnglishQuestionMapper.mapQuestionListToDto(paginatedQuestions);

        // Return the paginated page of questions
        return new PageImpl<>(questionDtos, pageable, allQuestions.size());
    }

    @Override
    public ObjectId submitTest(List<TestSubmissionRequest> submission) {
        return null;
    }

    @Override
    public TestResult getResult(ObjectId id) {
        return null;
    }


}
