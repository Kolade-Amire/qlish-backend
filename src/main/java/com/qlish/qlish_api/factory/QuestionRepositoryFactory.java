package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.repository.QuestionRepository;

public interface QuestionRepositoryFactory<T extends Question> {

    QuestionRepository<T> getRepository(TestSubject subject);
}
