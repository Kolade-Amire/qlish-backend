package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.strategy.ResultCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResultCalculationFactoryImpl implements ResultCalculationFactory {

    private final Map<String, ResultCalculationStrategy> strategies;

    @Override
    public ResultCalculationStrategy getStrategy(TestSubject testSubject) {
        ResultCalculationStrategy strategy = strategies.get(testSubject.getDisplayName());
        if (strategy == null) {
            throw new UnsupportedOperationException(AppConstants.UNSUPPORTED_TEST_TYPE);
        }
        return strategy;
    }
}
