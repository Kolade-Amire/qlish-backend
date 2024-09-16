package com.qlish.qlish_api.util;

import com.qlish.qlish_api.entity.TestQuestionDto;
import com.qlish.qlish_api.entity.TestResult;

import java.util.List;

public interface ResultCalculationStrategy {

    TestResult calculateResult(List<TestQuestionDto> questions);
}
