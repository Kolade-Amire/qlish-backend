package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.entity.TestResult;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TestResultCalculator {

    private final ResultCalculationStrategy strategy;

    public TestResult calculateResult(List<TestQuestionDto> questions) {
        return strategy.calculateResult(questions);
    }
}
