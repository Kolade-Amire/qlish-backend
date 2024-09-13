package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.*;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.EnglishTest;
import com.qlish.qlish_api.entity.EnglishTestFactory;
import com.qlish.qlish_api.entity.TestResult;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EnglishTestService {

    List<EnglishTest> getAllUserTests(ObjectId userId);

    EnglishTest getTestById(ObjectId id);

    EnglishTestDto getTestForView(ObjectId id);

    ObjectId saveTest(EnglishTest testEntity);

    void deleteTest(ObjectId id);

    ObjectId initiateNewTest(ObjectId userId, EnglishTestFactory factory);

    List<EnglishQuestionEntity> getQuestionsList(EnglishTestFactory testFactory);

    ObjectId submitTest(List<TestSubmissionRequest> submission);

    TestResult getResult(ObjectId id);

    Page<EnglishQuestionDto> getTestQuestionsForView(ObjectId testId, Pageable pageable);

}
