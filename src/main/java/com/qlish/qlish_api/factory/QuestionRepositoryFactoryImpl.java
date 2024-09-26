package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class QuestionRepositoryFactoryImpl implements QuestionRepositoryFactory {

    //initialize the map with injections of interfaces implementation
    private final Map<String, QuestionRepository> repositories;
    private final Map<String, ModifierFactory> modifierFactories;
    private final Map<String, QuestionMapperFactory> mappers;

    @Override
    public QuestionRepository getRepository(TestSubject subject) {
        QuestionRepository repository = repositories.get(subject.getDisplayName().toLowerCase());
        if (repository == null) {
            throw new IllegalArgumentException("Invalid subject " + subject);
        }
        return repository;
    }

    @Override
    public QuestionModifier getModifier(TestSubject subject, Map<String, String> requestParams) {
        ModifierFactory factory = modifierFactories.get(subject.getDisplayName().toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Invalid subject: " + subject);
        }
        return factory.createModifier(requestParams);
    }

    @Override
    public QuestionMapperFactory getMapper(TestSubject subject) {
        QuestionMapperFactory factory = mappers.get(subject.getDisplayName().toLowerCase());

        if (factory == null) {
            throw new IllegalArgumentException("Invalid subject: " + subject);
        }

        return factory;
    }
}
