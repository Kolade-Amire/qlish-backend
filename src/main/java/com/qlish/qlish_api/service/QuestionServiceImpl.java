package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.AdminQuestionViewRequest;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.factory.QuestionRepositoryFactory;
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

    private final QuestionRepositoryFactory questionRepositoryFactory;

    @Override
    public Page<QuestionDto> getQuestionsByCriteria(AdminQuestionViewRequest request, Pageable pageable) {

        var subject = TestSubject.getSubjectByDisplayName(request.getSubject().toLowerCase());
        var repository = questionRepositoryFactory.getRepository(subject);
        var modifier = questionRepositoryFactory.getModifier(subject, request.getModifiers());

        var questionsPage = repository.getAllQuestionsByCriteria(modifier, pageable);

       var mapperFactory = questionRepositoryFactory.getMapper(subject);

       return mapperFactory.mapToQuestionDtoPage(questionsPage, pageable);

    }

    @Override
    public ObjectId updateQuestion(QuestionDto questionDto) {
        return null;
    }

    @Override
    public void deleteQuestion(QuestionDto questionDto) {


    }

    @Override
    public ObjectId saveQuestion(Question question, TestSubject subject) {
        var repository = questionRepositoryFactory.getRepository(subject);
        var savedQuestion = repository.saveQuestion(question.getId(), question);
        return savedQuestion.getId();
    }

    @Override
    public Question getQuestionById(ObjectId id, TestSubject subject) {
        var repository = questionRepositoryFactory.getRepository(subject);
        return repository.getQuestionById(id);
    }
}
