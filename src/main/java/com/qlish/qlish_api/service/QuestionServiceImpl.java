package com.qlish.qlish_api.service;

import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.entity.CustomQuestion;
import com.qlish.qlish_api.enums.HandlerName;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.factory.HandlerFactory;
import com.qlish.qlish_api.handler.Handler;
import com.qlish.qlish_api.mapper.QuestionMapper;
import com.qlish.qlish_api.repository.CustomQuestionRepository;
import com.qlish.qlish_api.request.AdminQuestionViewRequest;
import com.qlish.qlish_api.request.NewQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
    private final CustomQuestionRepository questionRepository;
    private final MongoTemplate mongoTemplate;
    private final HandlerFactory handlerFactory;

    @PreAuthorize("hasAuthority('ADMIN_READ')")
    @Override
    public Page<QuestionDto> getQuestionsBySubjectAndCriteria(AdminQuestionViewRequest request, Pageable pageable) {

        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("subject").is(request.getSubject()));

            request.getModifiers().forEach((key, value) ->
                    query.addCriteria(Criteria.where("modifiers." + key).is(value))
            );

            // Set pagination and sorting parameters
            query.skip((long) pageable.getPageNumber() * pageable.getPageSize());
            query.limit(pageable.getPageSize());
            query.with(Sort.by(Sort.Direction.ASC, "_id"));

            // Execute query and get total count
            List<CustomQuestion> questions = mongoTemplate.find(query, CustomQuestion.class);
            long count = mongoTemplate.count(query.limit(-1).skip(-1), CustomQuestion.class);

            // Return results in a Page object
            var resultPage = new PageImpl<>(questions, pageable, count);

            return QuestionMapper.mapToQuestionDtoPage(resultPage, pageable);
        } catch (Exception e) {
            throw new CustomQlishException("Unable to retrieve questions. Check request and try again.", e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN_UPDATE')")
    @Override
    public QuestionDto updateQuestion(QuestionDto request) {

        try {
            var id = new ObjectId(request.getId());

            CustomQuestion question = findQuestionById(id);

            if (request.getQuestionText() != null) question.setQuestionText(question.getQuestionText());
            if (request.getOptions() != null) question.setOptions(request.getOptions());
            if (request.getAnswer() != null) question.setCorrectAnswer(request.getAnswer());
            if (request.getModifiers() != null) question.setModifiers(request.getModifiers());
            if (request.getSubject() != null) question.setSubject(request.getSubject());

            var savedQuestion = saveQuestion(question);
            return QuestionMapper.mapQuestionToQuestionDto(savedQuestion);
        } catch (Exception e) {
            throw new CustomQlishException("Failed to update question. Check request and try again", e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN_DELETE')")
    @Override
    public void deleteQuestion(String id) {
        try {
            var _id = new ObjectId(id);
            var question = findQuestionById(_id);
            questionRepository.delete(question);
        } catch (Exception e) {
            throw new CustomQlishException("Error occurred while deleting question", e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    @Override
    public QuestionDto addNewQuestion(NewQuestionRequest request) {
        try {
            TestSubject subject = TestSubject.getSubjectByDisplayName(request.getSubject());
            Handler handler = handlerFactory.getHandler(HandlerName.getHandlerNameBySubject(subject));

            boolean isValid = handler.validateNewQuestionRequest(request);

            if (!isValid) {
                throw new CustomQlishException("Validation failed for the provided new question request.");
            }

            CustomQuestion newQuestion = CustomQuestion.builder()
                    .subject(request.getSubject().toLowerCase())
                    .questionText(request.getQuestionText())
                    .options(request.getOptions())
                    .correctAnswer(request.getAnswer())
                    .modifiers(request.getModifiers())
                    .build();

            var savedQuestion = saveQuestion(newQuestion);

            return QuestionMapper.mapQuestionToQuestionDto(savedQuestion);

        } catch (Exception e) {
            throw new CustomQlishException("Error occurred while trying to add new question. Check request and try again", e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN_READ')")
    @Override
    public QuestionDto getQuestion(String id) {
        var _id = new ObjectId(id);
        var question = findQuestionById(_id);

        return QuestionMapper.mapQuestionToQuestionDto(question);
    }

    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    @Override
    public CustomQuestion saveQuestion(CustomQuestion question) {
        try {
            return questionRepository.save(question);
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
            throw new CustomQlishException("Unexpected error occurred while saving question: " + e.getMessage(), e);
        }

    }

    private CustomQuestion findQuestionById(ObjectId id) {
        return questionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Question not found")
        );
    }
}
