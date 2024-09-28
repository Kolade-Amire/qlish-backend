package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.AdminQuestionViewRequest;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.enums.TestSubject;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

    Page<QuestionDto> getQuestionsByCriteria(AdminQuestionViewRequest request, Pageable pageable);

    ObjectId updateQuestion(QuestionDto questionDto);

    void deleteQuestion(QuestionDto questionDto);

    <T extends Question> QuestionDto saveQuestion(T question, TestSubject subject);

    <T extends Question> T getQuestionById(ObjectId id, TestSubject subject);
}
