package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class QuestionRepositoryFactoryImpl implements QuestionRepositoryFactory<T > {

    private final Map<String, QuestionRepository> repositoryMap;
    private final Map<String, ModifierFactory> modifierFactoryMap;



    @Override
    public QuestionRepository getRepository(String subject) {
        QuestionRepository repository = repositoryMap.get(subject.toLowerCase());
        if (repository == null) {
            throw new IllegalArgumentException("Unknown subject: " + subject);
        }
        return repository;
    }

    @Override
    public Modifier getModifier(String subject, Map<String, String> requestParams) {
        ModifierFactory factory = modifierFactoryMap.get(subject.toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Unknown subject: " + subject);
        }
        return factory.createModifier(requestParams);
    }
}
