package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.entity.CustomQuestion;
import com.qlish.qlish_api.request.AdminQuestionViewRequest;
import com.qlish.qlish_api.request.NewQuestionRequest;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    Page<QuestionDto> getQuestionsBySubjectAndCriteria(AdminQuestionViewRequest request, Pageable pageable);

     QuestionDto updateQuestion(QuestionDto existingQuestion);

     QuestionDto getQuestion(String id);

     void deleteQuestion(String id);

     CustomQuestion saveQuestion(CustomQuestion question);

     QuestionDto addNewQuestion(NewQuestionRequest request);
}
