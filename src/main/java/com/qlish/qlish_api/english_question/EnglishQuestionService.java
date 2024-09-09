package com.qlish.qlish_api.english_question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

public interface EnglishQuestionService {

    Page<EnglishQuestionEntity> getEnglishQuestions(Pageable pageable, @Nullable EnglishQuestionLevel questionLevel, @Nullable EnglishQuestionClass questionClass, @Nullable EnglishQuestionTopic questionTopic, int testSize);
}
