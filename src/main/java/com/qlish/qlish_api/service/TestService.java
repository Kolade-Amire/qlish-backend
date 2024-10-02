package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.dto.TestDto;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.repository.QuestionRepository;
import com.qlish.qlish_api.request.TestRequest;
import com.qlish.qlish_api.request.TestSubmissionRequest;
import com.qlish.qlish_api.entity.TestEntity;
import com.qlish.qlish_api.entity.TestResult;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestService {

    TestDto getTestForView(ObjectId id);

    ObjectId saveTest(TestEntity testDto);

    <T extends Question, M extends QuestionModifier> String createTest (TestRequest request);

    Page<TestQuestionDto> getTestQuestions(ObjectId id, Pageable pageable);

    TestEntity getTestById(ObjectId id);

    String submitTest(ObjectId id, TestSubmissionRequest request);

    TestResult getTestResult(ObjectId id);

    void deleteTest(ObjectId id);

    void deleteAllUserTests(ObjectId userId);
}
