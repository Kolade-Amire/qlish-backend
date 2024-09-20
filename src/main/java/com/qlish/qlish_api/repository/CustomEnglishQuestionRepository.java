package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;

import java.util.List;


public interface CustomEnglishQuestionRepository {
    List<EnglishQuestionEntity> getTestQuestions(EnglishModifier modifier, int size);
}
