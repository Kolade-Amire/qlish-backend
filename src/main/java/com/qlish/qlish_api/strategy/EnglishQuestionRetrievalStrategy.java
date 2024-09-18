package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.service.EnglishQuestionService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class EnglishQuestionRetrievalStrategy implements QuestionRetrievalStrategy {

    private final EnglishQuestionService questionService;

    @Override
    public List<EnglishQuestionEntity> getQuestions(Map<String, String> modifiers, int size) {
        return questionService.getEnglishQuestions(modifiers,size);
    }
}
