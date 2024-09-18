package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.entity.Question;

import java.util.List;
import java.util.Map;

public interface QuestionRetrievalStrategy {

    List<? extends Question> getQuestions(Map<String,String> modifiers, int size);
}
