package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.repository.EnglishQuestionRepository;
import com.qlish.qlish_api.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnglishQuestionRepositoryFactory implements QuestionRepositoryFactory<EnglishQuestionEntity> {

    private final EnglishQuestionRepository repository;

    @Override
    public QuestionRepository<EnglishQuestionEntity> getRepository(TestSubject subject) {
        return repository;
    }
}
