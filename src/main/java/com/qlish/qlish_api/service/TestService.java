package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.QuestionViewDto;
import com.qlish.qlish_api.dto.TestDto;
import com.qlish.qlish_api.dto.TestRequest;
import com.qlish.qlish_api.dto.TestSubmissionRequest;
import com.qlish.qlish_api.entity.TestEntity;
import com.qlish.qlish_api.entity.TestResult;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestService {

    TestDto getTestForView(ObjectId id);

    ObjectId saveTest(TestEntity testDto);

    ObjectId createTest (TestRequest request);

    Page<QuestionViewDto> getTestQuestions(ObjectId id, Pageable pageable);

    TestEntity getTestById(ObjectId id);

    ObjectId submitTest(TestSubmissionRequest request);

    TestResult getTestResult(ObjectId id);

    void deleteTest(ObjectId id);

    void deleteAllUserTests(ObjectId userId);
}
