package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.service.EnglishQuestionService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EnglishQuestionRetrievalStrategy implements QuestionRetrievalStrategy<EnglishModifier> {

    private final EnglishQuestionService questionService;

    @Override
    public List<EnglishQuestionEntity> getQuestions(EnglishModifier modifier, int size) {
        return questionService.getEnglishQuestions(modifier,size);
    }
}
