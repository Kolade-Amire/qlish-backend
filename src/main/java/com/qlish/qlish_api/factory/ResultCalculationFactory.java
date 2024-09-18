package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.strategy.EnglishResultCalculationStrategy;
import com.qlish.qlish_api.strategy.ResultCalculationStrategy;

import java.util.HashMap;
import java.util.Map;

public class ResultCalculationFactory {

    private static final Map<TestSubject, ResultCalculationStrategy> strategies = new HashMap<>();

    static {
        strategies.put(TestSubject.ENGLISH, new EnglishResultCalculationStrategy());
        //other strategies go here as the app grows
    }

    public static ResultCalculationStrategy getStrategy(TestSubject testSubject) {
        ResultCalculationStrategy strategy = strategies.get(testSubject);
        if (strategy == null) {
            throw new UnsupportedOperationException(AppConstants.UNSUPPORTED_TEST_TYPE);
        }
        return strategy;
    }
}
