package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.mapper.EnglishQuestionMapper;
import com.qlish.qlish_api.repository.CustomEnglishQuestionRepository;
import com.qlish.qlish_api.repository.QuestionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class QuestionFactoryImpl implements QuestionFactory {

    private final Map<String, QuestionRepository<? extends Question>> repositories = new HashMap<>();
    private final Map<String, ModifierFactory<? extends QuestionModifier>> modifierFactories = new HashMap<>();
    private final Map<String, QuestionMapper<? extends Question>> mappers = new HashMap<>();

    private final CustomEnglishQuestionRepository customEnglishQuestionRepository;
    private final EnglishModifierFactory englishModifierFactory;
    private final EnglishQuestionMapper englishQuestionMapper;

    @PostConstruct
    void  init(){
        repositories.put("english", customEnglishQuestionRepository);
        modifierFactories.put("english", englishModifierFactory);
        mappers.put("english", englishQuestionMapper);

    }
    @Override
    public <T extends Question> QuestionRepository<T> getRepository(TestSubject subject) {
        var repository = repositories.get(subject.getDisplayName().toLowerCase());
        if (repository == null) {
            throw new IllegalArgumentException("Invalid subject " + subject);
        }
        return (QuestionRepository<T>) repository;
    }

    @Override
    public <T extends  QuestionModifier> T getModifier(TestSubject subject, Map<String, String> requestParams) {
        var factory = modifierFactories.get(subject.getDisplayName().toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Invalid subject: " + subject);
        }
        return (T) factory.createModifier(requestParams);
    }

    @Override
    public <T extends  Question> QuestionMapper<T> getMapper(TestSubject subject) {
        var factory = mappers.get(subject.getDisplayName().toLowerCase());

        if (factory == null) {
            throw new IllegalArgumentException("Invalid subject: " + subject);
        }

        return (QuestionMapper<T>) factory;
    }
}
