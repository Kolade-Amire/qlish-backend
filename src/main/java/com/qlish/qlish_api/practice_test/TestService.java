package com.qlish.qlish_api.practice_test;

import com.qlish.qlish_api.english_question.EnglishQuestionEntity;
import com.qlish.qlish_api.practice_test.english_test.EnglishQuestionDto;
import com.qlish.qlish_api.practice_test.english_test.EnglishTestRequest;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {

    List<TestEntity> findTestsByUserId(ObjectId userId);

    TestEntity findTestById(ObjectId id);

    void save(TestEntity testEntity);

    void delete(TestEntity testEntity);

    Page<EnglishQuestionDto> startNewEnglishTest(EnglishTestRequest englishTestRequest, Pageable pageable);

}
