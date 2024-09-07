package com.qlish.qlish_api.english_question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomEnglishQuestionRepository {
    Page<EnglishQuestionEntity> findQuestionsByCriteria(Pageable pageable, String questionLevel, String questionClass, String questionTopic, int limit);
}
