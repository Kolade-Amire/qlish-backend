package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.AdminQuestionViewRequest;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.factory.QuestionRepositoryFactory;
import lombok.RequiredArgsConstructor;
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
}
