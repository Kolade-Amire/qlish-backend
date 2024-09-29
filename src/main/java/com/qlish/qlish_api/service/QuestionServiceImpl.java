package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.AdminQuestionViewRequest;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.dto.QuestionRequest;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.factory.QuestionFactory;
import com.qlish.qlish_api.mapper.QuestionMapper;
import com.qlish.qlish_api.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionFactory questionFactory;

    @Override
    public <T extends Question> Page<QuestionDto> getQuestionsByCriteria(AdminQuestionViewRequest request, Pageable pageable) {

        var subject = TestSubject.getSubjectByDisplayName(request.getSubject().toLowerCase());

        QuestionRepository<T> repository = questionFactory.getRepository(subject);
        var modifier = questionFactory.getModifier(subject, request.getModifiers());

        var questionsPage = repository.getAllQuestionsByCriteria(modifier, pageable);

        QuestionMapper<T> mapper = questionFactory.getMapper(subject);

        return mapper.mapToQuestionDtoPage(questionsPage, pageable);

    }

    @Override
    public <T extends Question> QuestionDto updateQuestion(ObjectId id, QuestionRequest request) {

        var subject = TestSubject.getSubjectByDisplayName(request.getQuestionText());
        T question = getQuestionById(id, subject);

        question.setQuestionText(request.getQuestionText());
        question.setOptions(request.getOptions());
        question.setAnswer(request.getAnswer());

        return saveQuestion(question, subject);
    }

    @Override
    public <T extends Question> void deleteQuestion(QuestionDto questionDto) {
        var subject = TestSubject.getSubjectByDisplayName(questionDto.getQuestionText());
        QuestionRepository<T> repository = questionFactory.getRepository(subject);
        T question = getQuestionById(questionDto.getId(), subject);
        repository.deleteQuestion(question);

    }

    @Override
    public <T extends Question> QuestionDto addNewQuestion(QuestionRequest questionRequest) {
        var subject = TestSubject.getSubjectByDisplayName(questionRequest.getSubject());
        QuestionMapper<T> mapper = questionFactory.getMapper(subject);
        T newQuestion = mapper.mapQuestionRequestToQuestion(questionRequest);

        return saveQuestion(newQuestion, subject);

    }

    @Override
    public <T extends Question> QuestionDto saveQuestion(T question, TestSubject subject) {
        QuestionRepository<T> repository = questionFactory.getRepository(subject);
        var savedQuestion = repository.saveQuestion(question);
        QuestionMapper<T> mapper = questionFactory.getMapper(subject);

        return mapper.mapQuestionToQuestionDto(savedQuestion);
    }

    @Override
    public <T extends Question> T getQuestionById(ObjectId id, TestSubject subject) {
        QuestionRepository<T> repository = questionFactory.getRepository(subject);
        return repository.getQuestionById(id).orElseThrow(
                () -> new EntityNotFoundException("Question not found")
        );
    }
}
