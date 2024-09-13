package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.EnglishQuestionDto;
import com.qlish.qlish_api.dto.TestRequest;
import com.qlish.qlish_api.entity.EnglishTestFactory;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final EnglishTestService englishTestService;

    @Override
    public ObjectId createEnglishTest (TestRequest testRequest) {

        var testFactory = new EnglishTestFactory();
        testFactory.setSubject(testRequest.getTestSubject());
        testFactory.validateTest();

        var modifier = testRequest.getModifier();

        testFactory.setQuestionClass(modifier.get("class"));
        testFactory.setQuestionLevel(modifier.get("level"));
        testFactory.setQuestionTopic(modifier.get("topic"));
        testFactory.setQuestionCount(testRequest.getQuestionCount());


        return englishTestService.initiateNewTest(testRequest.getUserId(), testFactory);

    }

    @Override
    public Page<EnglishQuestionDto> startEnglishTest(ObjectId testId, Pageable pageable) {
        return englishTestService.getTestQuestionsForView(testId, pageable);
    }
}
