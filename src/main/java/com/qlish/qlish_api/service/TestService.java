package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.QuestionViewDto;
import com.qlish.qlish_api.dto.TestRequest;
import com.qlish.qlish_api.dto.TestSubmissionRequest;
import com.qlish.qlish_api.entity.TestResult;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {

    ObjectId createTest(TestRequest testRequest);
    Page<QuestionViewDto> startTest(ObjectId testId, Pageable pageable);

    ObjectId submitTest(TestSubmissionRequest request);

    TestResult getResult(ObjectId testId);

}
