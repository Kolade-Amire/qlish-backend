package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.mapper.QuestionMapper;
import com.qlish.qlish_api.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class QuestionFactoryImpl implements QuestionFactory {

    //initialize the map with injections of interfaces implementation
    private final Map<String, QuestionRepository<? extends Question>> repositories;
    private final Map<String, ModifierFactory> modifierFactories;
    private final Map<String, QuestionMapper<? extends Question>> mappers;

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Question> QuestionRepository<T> getRepository(TestSubject subject) {
        var repository = repositories.get(subject.getDisplayName().toLowerCase());
        if (repository == null) {
            throw new IllegalArgumentException("Invalid subject " + subject);
        }
        return (QuestionRepository<T>) repository;
    }

    @Override
    public QuestionModifier getModifier(TestSubject subject, Map<String, String> requestParams) {
        ModifierFactory factory = modifierFactories.get(subject.getDisplayName().toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Invalid subject: " + subject);
        }
        return factory.createModifier(requestParams);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends  Question> QuestionMapper<T> getMapper(TestSubject subject) {
        var factory = mappers.get(subject.getDisplayName().toLowerCase());

        if (factory == null) {
            throw new IllegalArgumentException("Invalid subject: " + subject);
        }

        return (QuestionMapper<T>) factory;
    }
}
