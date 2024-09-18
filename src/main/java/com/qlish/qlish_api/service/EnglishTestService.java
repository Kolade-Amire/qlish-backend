package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.*;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.EnglishTest;
import com.qlish.qlish_api.factory.EnglishTestFactory;
import com.qlish.qlish_api.entity.TestResult;
import com.qlish.qlish_api.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EnglishTestService implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(EnglishTestService.class);


    private final TestRepository testRepository;
    private final EnglishQuestionService englishQuestionService;


    @Override
    public ObjectId createTest(TestRequest testRequest) {
        return null;
    }

    @Override
    public Page<QuestionViewDto> startTest(ObjectId testId, Pageable pageable) {
        return null;
    }

    @Override
    public ObjectId submitTest(TestSubmissionRequest request) {
        return null;
    }

    @Override
    public TestResult getResult(ObjectId testId) {
        return null;
    }
}
