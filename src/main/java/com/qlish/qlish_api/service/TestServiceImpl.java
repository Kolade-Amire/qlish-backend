package com.qlish.qlish_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.dto.TestDto;
import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.entity.*;
import com.qlish.qlish_api.enums.HandlerName;
import com.qlish.qlish_api.enums.TestStatus;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.exception.*;
import com.qlish.qlish_api.factory.HandlerFactory;
import com.qlish.qlish_api.generativeAI.GeminiAI;
import com.qlish.qlish_api.mapper.TestMapper;
import com.qlish.qlish_api.mapper.TestQuestionMapper;
import com.qlish.qlish_api.repository.CustomQuestionRepository;
import com.qlish.qlish_api.repository.TestRepository;
import com.qlish.qlish_api.request.TestQuestionSubmissionRequest;
import com.qlish.qlish_api.request.TestRequest;
import com.qlish.qlish_api.request.TestSubmissionRequest;
import com.qlish.qlish_api.strategy.ResultCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
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
    private final GeminiAI geminiAI;
    private final HandlerFactory handlerFactory;
    private final ResultCalculationStrategy resultCalculationStrategy;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TestRepository testRepository;
    private final CustomQuestionRepository customQuestionRepository;


    @Override
    public TestEntity getTestById(ObjectId id) {
        return testRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Test not found with ID: " + id)
        );
    }

    @Override
    public TestDto getTestForView(String id) {
        var test = getTestById(returnObjectId(id));
        return TestMapper.mapTestToDto(test);
    }


    @Override
    public ObjectId saveTest(TestEntity test) {
        try {
            return testRepository.save(test).getId();
        } catch (MongoWriteException e) {
            logger.error("Mongo write error: {}", e.getMessage());
            throw new CustomQlishException("Error writing to MongoDB: " + e.getMessage(), e);
        } catch (MongoTimeoutException e) {
            logger.error("Mongo timeout error: {}", e.getMessage());
            throw new CustomQlishException("MongoDB connection timeout: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            logger.error("Data access error: {}", e.getMessage());
            throw new CustomQlishException("Data access error: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            throw new CustomQlishException("Unexpected error occurred while saving test: " + e.getMessage(), e);
        }
    }

    @Override
    public String createTest(TestRequest request) throws GenerativeAIException {

        var subject = TestSubject.getSubjectByDisplayName(request.getSubject());
        List<Question> generatedQuestions;


        try {
            generatedQuestions = generateQuestions(request);
        } catch (GenerativeAIException e) {
            throw new GenerativeAIException(e.getMessage());
        }

        TestDetails testDetails = TestDetails.builder()
                .userId(returnObjectId(request.getUserId()))
                .testSubject(subject)
                .startedAt(LocalDateTime.now())
                .totalQuestionCount(request.getCount())
                .isCompleted(false)
                .build();

        List<TestQuestion> testQuestions = TestQuestionMapper.mapQuestionListToSavedTestQuestionDto(generatedQuestions);

        var newTest = TestEntity.builder()
                .testDetails(testDetails)
                .testStatus(TestStatus.CREATED)
                .questions(testQuestions)
                .build();

        return saveTest(newTest).toHexString();

    }

    @Override
    public List<Question> generateQuestions(TestRequest request) throws GenerativeAIException {
        TestSubject subject = TestSubject.getSubjectByDisplayName(request.getSubject());
        var handlerName = HandlerName.getHandlerNameBySubject(subject);
        var testHandler = handlerFactory.getHandler(handlerName);

        boolean isTestRequestValid = testHandler.validateRequest(request.getSubject(), request.getModifiers());
        if (isTestRequestValid) {
            var prompt = testHandler.getPrompt(request);
            var systemInstruction = testHandler.getSystemInstruction();

            try {
                String generatedQuestions = geminiAI.generateQuestions(prompt, systemInstruction);

                logger.info("Response from Gemini: {}", generatedQuestions);
                var cleanedQuestions = getQuestionsFromResponse(generatedQuestions);
                var questionsList = testHandler.parseJsonQuestions(cleanedQuestions);
                return saveGeneratedQuestions(questionsList);
            } catch (GenerativeAIException | JsonProcessingException e) {
                throw new GenerativeAIException(e.getMessage());
            }
        }
        throw new GenerativeAIException("Unable to generate questions, check request and try again.");
    }

    private String getQuestionsFromResponse(String jsonResponse) throws JsonProcessingException {
        //get questions list from returned json response
        JsonNode root = objectMapper.readTree(jsonResponse);
        String escapedQuestionsJson = root.at(
                "/candidates/0/content/parts/0/text"
        ).asText();

        //unescape the questions list json string and clean it
        return StringEscapeUtils.unescapeJson(escapedQuestionsJson)
                .replaceAll("^```json|```$", "")
                .trim();
    }

    private List<Question> saveGeneratedQuestions(List<Question> generatedQuestions) {

        try {
            var questions = customQuestionRepository.saveAll(generatedQuestions);
            questions.forEach(question -> logger.info(question.toString()));
            return questions;
        } catch (CustomQlishException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (MongoTimeoutException e) {
            logger.error("Mongo timeout error occurred: {} ", e.getMessage());
            throw new CustomQlishException("MongoDB connection timeout: " + e.getMessage(), e);
        } catch (MongoBulkWriteException e) {
            throw new RuntimeException("MongoBulkWriteException: ", e);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while saving generative questions: ", e);
        }

    }

    @Override
    public void deleteTest(String testId) {
        var test = getTestById(returnObjectId(testId));
        testRepository.delete(test);
    }

    @Override
    public Page<TestQuestionDto> getTestQuestions(String testId, Pageable pageable) {

        var test = getTestById(returnObjectId(testId));

        var questions = test.getQuestions();

        // Perform pagination over the full list of questions
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), questions.size());
        var pageQuestionList = questions.subList(start, end);


        List<TestQuestionDto> questionDtoList = TestQuestionMapper.mapQuestionListToTestViewDto(pageQuestionList);

        // Return the paginated page of questions
        return new PageImpl<>(questionDtoList, pageable, questions.size());
    }

    @Override
    public String submitTest(TestSubmissionRequest request) {
        try {
            var test = getTestById(returnObjectId(request.getId()));
            var answers = request.getAnswers();

            // Process each answer and map it to the corresponding question
            for (TestQuestionSubmissionRequest submission : answers) {
                // Find the testQuestion in the test
                TestQuestion testQuestion = test.getQuestions().stream()
                        .filter(q -> q.getId().equals(submission.getQuestionId()))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException(AppConstants.QUESTION_NOT_FOUND));

                // Store the submission in the corresponding question of the test
                testQuestion.setSelectedOption(submission.getSelectedOption());
                testQuestion.setAnswerCorrect(submission.getSelectedOption().equalsIgnoreCase(testQuestion.getCorrectAnswer()));
            }

            // Mark the test as completed
            test.getTestDetails().setCompleted(true);

            saveTest(test);

            return test.getId().toHexString();
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            throw new TestSubmissionException(AppConstants.TEST_SUBMISSION_ERROR);
        }

    }

    @Override
    public TestResult getTestResult(String id) {
        try {
            var test = getTestById(returnObjectId(id));
            return resultCalculationStrategy.calculateResult(test.getQuestions());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new TestResultException(e.getMessage());
        }
    }

    private ObjectId returnObjectId(String idString){
        return new ObjectId(idString);
    }


}
