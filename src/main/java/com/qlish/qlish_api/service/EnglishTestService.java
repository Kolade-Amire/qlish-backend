package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.EnglishQuestionDto;
import com.qlish.qlish_api.dto.EnglishTestRequest;
import com.qlish.qlish_api.dto.TestSubmissionRequest;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.EnglishTest;
import com.qlish.qlish_api.entity.TestResult;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EnglishTestService {

    List<EnglishTest> getAllUserTests(ObjectId userId);

    EnglishTest findTestById(ObjectId id);

    ObjectId saveTest(EnglishTest testEntity);

    void deleteTest(ObjectId id);

    ObjectId initiateNewTest(EnglishTestRequest englishTestRequest);

    public List<EnglishQuestionEntity> getEnglishQuestions(EnglishTestRequest testRequest);

    ObjectId submitTest(List<TestSubmissionRequest> submission);

    TestResult getResult(ObjectId id);

    Page<EnglishQuestionDto> startTest(ObjectId testId, Pageable pageable);

}
