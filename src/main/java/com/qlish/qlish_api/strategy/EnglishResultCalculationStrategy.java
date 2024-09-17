package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.entity.TestQuestionDto;
import com.qlish.qlish_api.entity.TestResult;

import java.util.List;

public class EnglishResultCalculationStrategy implements ResultCalculationStrategy {
    @Override
    public TestResult calculateResult(List<TestQuestionDto> questions) {
        int correctAnswers = 0;
        int incorrectAnswers = 0;
        int scorePercentage = 0;
        for (TestQuestionDto question : questions) {
            if(question.isAnswerCorrect()){
                correctAnswers++;
            }else {
                incorrectAnswers++;
            }
        }
        scorePercentage = 100 * (correctAnswers/questions.size());

        return TestResult.builder()
                .totalQuestionCount(questions.size())
                .totalCorrectAnswers(correctAnswers)
                .totalIncorrectAnswers(incorrectAnswers)
                .scorePercentage(scorePercentage)
                .build();
    }
}
