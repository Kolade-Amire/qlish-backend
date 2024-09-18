package com.qlish.qlish_api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TestResult {
    private int totalQuestions;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private int scorePercentage;
}
