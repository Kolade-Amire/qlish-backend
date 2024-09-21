package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.strategy.ResultCalculationStrategy;

public interface ResultCalculationFactory {

    ResultCalculationStrategy getStrategy(TestSubject subject);
}
