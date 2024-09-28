package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository <T extends Question> {

   List<T> getTestQuestions(QuestionModifier modifier, int size);

   Page<T> getAllQuestionsByCriteria(QuestionModifier modifier, Pageable pageable);

   T saveQuestion(T question);

   void deleteQuestion(T question);

   Optional<T> getQuestionById(ObjectId id);
}
