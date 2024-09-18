package com.qlish.qlish_api.service;

import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionClass;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionLevel;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionTopic;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import org.springframework.lang.Nullable;

import java.util.List;

public interface EnglishQuestionService {

    List<EnglishQuestionEntity> getEnglishQuestions(EnglishModifier modifier, int testSize);
}
