package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.dto.CompletedTestQuestionDto;
import com.qlish.qlish_api.entity.TestResult;

import java.util.List;

public interface ResultCalculationStrategy {

    TestResult calculateResult(List<CompletedTestQuestionDto> questions);
}
