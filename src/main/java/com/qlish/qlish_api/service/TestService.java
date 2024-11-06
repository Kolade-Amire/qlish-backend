package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.dto.TestDto;
import com.qlish.qlish_api.entity.*;
import com.qlish.qlish_api.exception.GenerativeAIException;
import com.qlish.qlish_api.request.TestRequest;
import com.qlish.qlish_api.request.TestSubmissionRequest;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {

    TestDto getTestForView(ObjectId id);

    ObjectId saveTest(TestEntity testDto);

    String createTest (TestRequest request) throws GenerativeAIException;

    List<Question> generateQuestions(TestRequest request) throws GenerativeAIException;

    Page<TestQuestionDto> getTestQuestions(ObjectId id, Pageable pageable);

    TestEntity getTestById(ObjectId id);

    String submitTest(ObjectId id, TestSubmissionRequest request);

    TestResult getTestResult(ObjectId id);

    void deleteTest(String id);

    void deleteAllUserTests(String userId);
}
