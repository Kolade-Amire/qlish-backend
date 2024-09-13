package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.constants.english_enums.EnglishQuestionClass;
import com.qlish.qlish_api.constants.english_enums.EnglishQuestionLevel;
import com.qlish.qlish_api.constants.english_enums.EnglishQuestionTopic;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import org.springframework.lang.Nullable;

import java.util.List;


public interface CustomEnglishQuestionRepository {
    List<EnglishQuestionEntity> findQuestionsByCriteria (@Nullable EnglishQuestionLevel questionEnglishQuestionLevel, @Nullable EnglishQuestionClass englishQuestionClass, @Nullable EnglishQuestionTopic questionEnglishQuestionTopic, int limit);
}
