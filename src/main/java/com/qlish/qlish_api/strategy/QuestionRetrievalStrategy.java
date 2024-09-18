package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;

import java.util.List;

public interface QuestionRetrievalStrategy <T extends QuestionModifier> {

    List<? extends Question> getQuestions(T modifier, int size);
}
