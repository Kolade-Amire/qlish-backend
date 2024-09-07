package com.qlish.qlish_api.english_question;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnglishQuestionServiceImpl implements EnglishQuestionService {

    private final EnglishQuestionRepository repository;

    @Override
    public Page<EnglishQuestionEntity> getEnglishQuestions(Pageable pageable, Level questionLevel, QuestionClass questionClass, Topic questionTopic, int testSize) {

        repository.findQuestionsByCriteria(pageable, questionLevel, questionClass, questionTopic, testSize);
    }
}
