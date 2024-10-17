package com.qlish.qlish_api.service;

import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import com.qlish.qlish_api.request.AdminQuestionViewRequest;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.request.QuestionRequest;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.factory.QuestionFactory;
import com.qlish.qlish_api.mapper.QuestionMapper;
import com.qlish.qlish_api.repository.QuestionRepository;
import com.qlish.qlish_api.request.UpdateQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
    private final QuestionFactory questionFactory;

    @PreAuthorize("hasAuthority('ADMIN_READ')")
    @Override
    public <T extends Question> Page<QuestionDto> getQuestionsByCriteria(AdminQuestionViewRequest request, Pageable pageable) {

        var subject = TestSubject.getSubjectByDisplayName(request.getSubject().toLowerCase());

        QuestionRepository<T> repository = questionFactory.getRepository(subject);
        var modifier = questionFactory.getModifier(subject, request.getModifiers());

        var questionsPage = repository.getAllQuestionsByCriteria(modifier, pageable);

        QuestionMapper<T> mapper = questionFactory.getMapper(subject);

        return mapper.mapToQuestionDtoPage(questionsPage, pageable);

    }

    @PreAuthorize("hasAuthority('ADMIN_UPDATE')")
    @Override
    public <T extends Question> QuestionDto updateQuestion(ObjectId id, UpdateQuestionRequest request) {

        var subject = TestSubject.getSubjectByDisplayName(request.getQuestionText());
        T question = findQuestionById(id, subject);

        question.setQuestionText(request.getQuestionText());
        question.setOptions(request.getOptions());
        question.setAnswer(request.getAnswer());

        return saveQuestion(question, subject);
    }

    @PreAuthorize("hasAuthority('ADMIN_DELETE')")
    @Override
    public <T extends Question> void deleteQuestion(ObjectId id, String questionSubject) {
        var subject = TestSubject.getSubjectByDisplayName(questionSubject);
        QuestionRepository<T> repository = questionFactory.getRepository(subject);
        T question = findQuestionById(id, subject);
        repository.deleteQuestion(question);
    }

    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    @Override
    public <T extends Question> QuestionDto addNewQuestion(QuestionRequest questionRequest) {
        var subject = TestSubject.getSubjectByDisplayName(questionRequest.getSubject());
        QuestionMapper<T> mapper = questionFactory.getMapper(subject);
        T newQuestion = mapper.mapQuestionRequestToQuestion(questionRequest);

        return saveQuestion(newQuestion, subject);

    }

    @PreAuthorize("hasAuthority('ADMIN_READ')")
    @Override
    public <T extends Question> QuestionDto getQuestion(ObjectId id, String questionSubject) {
        TestSubject subject = TestSubject.getSubjectByDisplayName(questionSubject);
        T question = findQuestionById(id, subject);
        QuestionMapper<T> mapper = questionFactory.getMapper(subject);

        return mapper.mapQuestionToQuestionDto(question);
    }

    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    @Override
    public <T extends Question> QuestionDto saveQuestion(T question, TestSubject subject) {
        QuestionRepository<T> repository = questionFactory.getRepository(subject);

        try {
            var savedQuestion = repository.saveQuestion(question);
            QuestionMapper<T> mapper = questionFactory.getMapper(subject);
            return mapper.mapQuestionToQuestionDto(savedQuestion);
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

    private <T extends Question> T findQuestionById(ObjectId id, TestSubject subject) {
        QuestionRepository<T> repository = questionFactory.getRepository(subject);
        return repository.getQuestionById(id).orElseThrow(
                () -> new EntityNotFoundException("Question not found")
        );
    }
}
