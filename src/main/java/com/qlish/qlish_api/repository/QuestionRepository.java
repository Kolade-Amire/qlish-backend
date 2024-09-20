package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;

import java.util.List;

public interface QuestionRepository <T extends Question> {

   <M extends QuestionModifier> List<T> getTestQuestions(M modifier, int size);

}
