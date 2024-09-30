package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.request.AdminQuestionViewRequest;
import com.qlish.qlish_api.request.QuestionRequest;
import com.qlish.qlish_api.request.UpdateQuestionRequest;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

    <T extends Question> Page<QuestionDto> getQuestionsByCriteria(AdminQuestionViewRequest request, Pageable pageable);

    <T extends Question> QuestionDto updateQuestion(ObjectId id, UpdateQuestionRequest request);

    <T extends Question> QuestionDto getQuestion(ObjectId id, String subject);

    <T extends Question> void deleteQuestion(ObjectId id, String subject);

    <T extends Question> QuestionDto saveQuestion(T question, TestSubject subject);

    <T extends Question> T findQuestionById(ObjectId id, TestSubject subject);

    <T extends Question> QuestionDto addNewQuestion(QuestionRequest request);
}
