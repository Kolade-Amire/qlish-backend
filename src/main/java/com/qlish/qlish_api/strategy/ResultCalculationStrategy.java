package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.model.TestQuestion;
import com.qlish.qlish_api.model.TestResult;

import java.util.List;

public interface ResultCalculationStrategy {

    TestResult calculateResult(List<TestQuestion> questions);
}
