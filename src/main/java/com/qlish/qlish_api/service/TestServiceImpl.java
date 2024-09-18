//package com.qlish.qlish_api.service;
//
//import com.qlish.qlish_api.dto.QuestionViewDto;
//import com.qlish.qlish_api.dto.TestRequest;
//import com.qlish.qlish_api.dto.TestSubmissionRequest;
//import com.qlish.qlish_api.entity.TestResult;
//import com.qlish.qlish_api.enums.TestSubject;
//import com.qlish.qlish_api.factory.EnglishTestFactory;
//import lombok.RequiredArgsConstructor;
//import org.bson.types.ObjectId;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class TestServiceImpl implements TestService {
//
//    private final EnglishTestService englishTestService;
//
//
//    @Override
//    public List<String> takeATest() {
//        return List.of(Arrays.stream(TestSubject.values()).map(
//                TestSubject::getDisplayName
//        ).toString());
//    }
//
//    //TODO: implement an abstraction over test creation
//    @Override
//    public ObjectId createEnglishTest (TestRequest testRequest) {
//
//        var testFactory = new EnglishTestFactory();
//        testFactory.setSubject(testRequest.getTestSubject());
//        testFactory.validateTest();
//
//        var modifier = testRequest.getModifier();
//
//        testFactory.setQuestionClass(modifier.get("class"));
//        testFactory.setQuestionLevel(modifier.get("level"));
//        testFactory.setQuestionTopic(modifier.get("topic"));
//        testFactory.setQuestionCount(testRequest.getQuestionCount());
//
//
//        return englishTestService.initiateNewTest(testRequest.getUserId(), testFactory);
//
//    }
//
//    @Override
//    public Page<QuestionViewDto> startEnglishTest(ObjectId testId, Pageable pageable) {
//        return englishTestService.getTestQuestionsForView(testId, pageable);
//    }
//
//    @Override
//    public ObjectId submitTest(TestSubmissionRequest request) {
//        return englishTestService.submitTest(request);
//    }
//
//    @Override
//    public TestResult calculateResult(ObjectId testId) {
//        return null;
//    }
//}
