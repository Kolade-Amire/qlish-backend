package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.service.EnglishTestService;
import com.qlish.qlish_api.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TestServiceFactory {
    private final EnglishTestService englishTestService;

    private static final Map<TestSubject, TestService> services = new HashMap<>();

    static  {
        services.put(TestSubject.ENGLISH, new EnglishTestService());
    }

    public TestService getService(TestSubject testSubject) {
        TestService service = services.get(testSubject);
        if (service == null) {
            throw new RuntimeException(AppConstants.UNSUPPORTED_TEST_TYPE);
        }

        return service;
    }

}
