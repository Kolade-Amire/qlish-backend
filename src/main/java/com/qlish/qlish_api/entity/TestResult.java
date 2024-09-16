package com.qlish.qlish_api.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResult {
    private int totalQuestionCount;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private int scorePercentage;
}
