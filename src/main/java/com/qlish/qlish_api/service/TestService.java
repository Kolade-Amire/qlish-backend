package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.EnglishQuestionDto;
import com.qlish.qlish_api.dto.TestRequest;
import com.qlish.qlish_api.dto.TestSubmissionRequest;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {



    ObjectId createEnglishTest(TestRequest testRequest);
    Page<EnglishQuestionDto> startEnglishTest(ObjectId testId, Pageable pageable);

    List<String> takeATest();

    ObjectId submitTest(TestSubmissionRequest request);

}
