package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.EnglishQuestionDto;
import org.springframework.data.domain.Page;

public interface QuestionService {

    Page<EnglishQuestionDto> getAllQuestions();
}
