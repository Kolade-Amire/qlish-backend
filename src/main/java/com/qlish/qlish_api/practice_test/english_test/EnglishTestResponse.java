package com.qlish.qlish_api.practice_test.english_test;

import com.qlish.qlish_api.english_question.EnglishQuestionEntity;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

@Builder
@RequiredArgsConstructor
public class EnglishTestResponse {
    ObjectId testId;
    ObjectId userId;
    Page<EnglishQuestionEntity> questions;
    Integer totalCount;
}
