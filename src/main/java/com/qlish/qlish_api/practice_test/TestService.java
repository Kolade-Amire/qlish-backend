package com.qlish.qlish_api.practice_test;

import com.qlish.qlish_api.practice_test.english_test.EnglishQuestionDto;
import com.qlish.qlish_api.practice_test.english_test.EnglishTestRequest;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
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
