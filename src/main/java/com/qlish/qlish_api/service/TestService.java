package com.qlish.qlish_api.service;

import com.qlish.qlish_api.entity.TestEntity;
import com.qlish.qlish_api.entity.TestResult;
import com.qlish.qlish_api.dto.TestSubmissionRequest;
import com.qlish.qlish_api.dto.EnglishTestRequest;
import com.qlish.qlish_api.dto.EnglishTestDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {

    List<TestEntity> findTestsByUserId(ObjectId userId);

    TestEntity findTestById(ObjectId id);

    ObjectId saveTest(TestEntity testEntity);

    void delete(TestEntity testEntity);

    EnglishTestDto startNewEnglishTest(EnglishTestRequest englishTestRequest, Pageable pageable);

    ObjectId submitTest(List<TestSubmissionRequest> submission);

    TestResult getResult(ObjectId id);

}
