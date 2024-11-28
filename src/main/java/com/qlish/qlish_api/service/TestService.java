package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.dto.TestDto;
import com.qlish.qlish_api.model.*;
import com.qlish.qlish_api.exception.GenerativeAIException;
import com.qlish.qlish_api.request.TestRequest;
import com.qlish.qlish_api.request.TestSubmissionRequest;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {

    TestDto getTestForView(String id);

    ObjectId saveTest(TestEntity testDto);

    String createTest (TestRequest request) throws GenerativeAIException;

    List<Question> generateQuestions(TestRequest request) throws GenerativeAIException;

    Page<TestQuestionDto> getTestQuestions(String id, Pageable pageable);

    TestEntity getTestById(ObjectId id);

    String submitTest(TestSubmissionRequest request);

    TestResult getTestResult(String id);

    void deleteTest(String id);
}
