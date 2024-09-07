package com.qlish.qlish_api.english_question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface EnglishQuestionService {

    Page<EnglishQuestionEntity> getEnglishQuestions(Pageable pageable, @Nullable  Level questionLevel, @Nullable QuestionClass questionClass, @Nullable Topic questionTopic, int testSize);
}
