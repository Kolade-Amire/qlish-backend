package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.entity.TestQuestion;
import com.qlish.qlish_api.entity.TestResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResulCalculationStrategyImpl implements ResultCalculationStrategy {
    @Override
    public TestResult calculateResult(List<TestQuestion> questions) {
        int correctAnswers = 0;
        int incorrectAnswers = 0;
        for (TestQuestion question : questions) {
            if(question.isAnswerCorrect()){
                correctAnswers++;
            }else {
                incorrectAnswers++;
            }
        }
        int scorePercentage = 100 * (correctAnswers/questions.size());

        return TestResult.builder()
                .totalQuestions(questions.size())
                .totalCorrectAnswers(correctAnswers)
                .totalIncorrectAnswers(incorrectAnswers)
                .scorePercentage(scorePercentage)
                .build();
    }
}
