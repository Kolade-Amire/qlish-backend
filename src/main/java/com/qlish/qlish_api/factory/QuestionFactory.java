package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.mapper.QuestionMapper;
import com.qlish.qlish_api.repository.QuestionRepository;

import java.util.Map;

public interface QuestionFactory {

    <T extends Question> QuestionRepository<T> getRepository(TestSubject subject);

    <T extends QuestionModifier> T getModifier(TestSubject subject, Map<String, String> requestParams);

    <T extends Question> QuestionMapper<T> getMapper(TestSubject subject);
}
