package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.model.TestQuestion;
import com.qlish.qlish_api.model.TestResult;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface ScoreGradingStrategy extends Function<List<TestQuestion>, TestResult> {

    static ScoreGradingStrategy calculateTestScore() {
        return testQuestions -> {
            var correctAnswers = (int) testQuestions.stream().filter(TestQuestion::isAnswerCorrect).count();
            var incorrectAnswers = testQuestions.size() - correctAnswers;
            int scorePercentage = 100 * (correctAnswers / testQuestions.size());
            return TestResult.builder()
                    .totalQuestions(testQuestions.size())
                    .totalCorrectAnswers(correctAnswers)
                    .totalIncorrectAnswers(incorrectAnswers)
                    .scorePercentage(scorePercentage)
                    .build();
        };
    }
}
