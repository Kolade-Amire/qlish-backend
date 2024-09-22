package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.EnglishQuestionDto;
import com.qlish.qlish_api.repository.EnglishQuestionRepository;
import com.qlish.qlish_api.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final EnglishQuestionRepository questionRepository;

    @Override
    public Page<EnglishQuestionDto> getAllQuestions() {
        return null;
    }
}
