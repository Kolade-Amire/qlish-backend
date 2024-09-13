package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.EnglishTestRequest;
import com.qlish.qlish_api.dto.TestSubmissionRequest;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.EnglishTest;
import com.qlish.qlish_api.entity.TestResult;
import org.bson.types.ObjectId;

import java.util.List;

public interface EnglishTestService {

    List<EnglishTest> findAllTestsByUser(ObjectId userId);

    EnglishTest findTestById(ObjectId id);

    ObjectId saveTest(EnglishTest testEntity);

    void delete(ObjectId id);

    ObjectId initiateNewTest(EnglishTestRequest englishTestRequest);

    public List<EnglishQuestionEntity> getEnglishQuestions(EnglishTestRequest testRequest);

    ObjectId submitTest(List<TestSubmissionRequest> submission);

    TestResult getResult(ObjectId id);

}
