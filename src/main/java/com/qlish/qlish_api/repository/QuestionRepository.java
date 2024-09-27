package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionRepository {

   List<? extends Question> getTestQuestions(QuestionModifier modifier, int size);

   Page<? extends Question> getAllQuestionsByCriteria(QuestionModifier modifier, Pageable pageable);

   Question saveQuestion(ObjectId id, Question question);
   <T extends Question> T updateQuestion(T question);
   void deleteQuestion(ObjectId id);

   <T extends Question> T getQuestionById(ObjectId id);
}
