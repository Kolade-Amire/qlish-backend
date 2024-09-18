package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;

import java.util.List;
import java.util.Map;


public interface CustomEnglishQuestionRepository {
    List<EnglishQuestionEntity> findQuestionsByCriteria (EnglishModifier modifier, int size);
}
