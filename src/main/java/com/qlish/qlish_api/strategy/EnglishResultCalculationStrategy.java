package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.dto.CompletedTestQuestionDto;
import com.qlish.qlish_api.entity.TestResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("english")
public class EnglishResultCalculationStrategy implements ResultCalculationStrategy {
    @Override
    public TestResult calculateResult(List<CompletedTestQuestionDto> questions) {
        int correctAnswers = 0;
        int incorrectAnswers = 0;
        int scorePercentage = 0;
        for (CompletedTestQuestionDto question : questions) {
            if(question.isAnswerCorrect()){
                correctAnswers++;
            }else {
                incorrectAnswers++;
            }
        }
        scorePercentage = 100 * (correctAnswers/questions.size());

        return TestResult.builder()
                .totalQuestions(questions.size())
                .totalCorrectAnswers(correctAnswers)
                .totalIncorrectAnswers(incorrectAnswers)
                .scorePercentage(scorePercentage)
                .build();
    }
}
