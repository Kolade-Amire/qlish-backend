package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.repository.QuestionRepository;

import java.util.Map;

public interface QuestionRepositoryFactory {

    QuestionRepository getRepository(TestSubject subject);

    QuestionModifier getModifier(TestSubject subject, Map<String, String> requestParams);
}
