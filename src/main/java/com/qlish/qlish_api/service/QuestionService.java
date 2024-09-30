package com.qlish.qlish_api.service;

import com.qlish.qlish_api.request.AdminQuestionViewRequest;
import com.qlish.qlish_api.request.QuestionRequest;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.enums.TestSubject;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

    <T extends Question> Page<QuestionDto> getQuestionsByCriteria(AdminQuestionViewRequest request, Pageable pageable);

    <T extends Question> QuestionDto updateQuestion(ObjectId id, QuestionRequest request);

    <T extends Question> void deleteQuestion(ObjectId id, String subject);

    <T extends Question> QuestionDto saveQuestion(T question, TestSubject subject);

    <T extends Question> T getQuestionById(ObjectId id, TestSubject subject);

    <T extends Question> QuestionDto addNewQuestion(QuestionRequest request);
}
