package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.strategy.EnglishQuestionRetrievalStrategy;
import com.qlish.qlish_api.strategy.QuestionRetrievalStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class QuestionRetrievalFactory {

    private final Map<TestSubject, QuestionRetrievalStrategy<? extends QuestionModifier>> strategies;
    private final EnglishQuestionRetrievalStrategy englishQuestionRetrievalStrategy;



    public QuestionRetrievalFactory(EnglishQuestionRetrievalStrategy englishQuestionRetrievalStrategy) {
        this.englishQuestionRetrievalStrategy = englishQuestionRetrievalStrategy;

        this.strategies = Map.of(
                TestSubject.ENGLISH, getEnglishQuestionRetrievalStrategy()
        );
    }

    public QuestionRetrievalStrategy<? extends QuestionModifier> getStrategy(TestSubject subject) {

        return Optional.ofNullable(strategies.get(subject))
                .orElseThrow(() -> new IllegalArgumentException("No strategy found for subject: " + subject));
    }

    private EnglishQuestionRetrievalStrategy getEnglishQuestionRetrievalStrategy() {
        return englishQuestionRetrievalStrategy;
    }


}
