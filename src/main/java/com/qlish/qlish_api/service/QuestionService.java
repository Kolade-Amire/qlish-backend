package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.request.AdminQuestionViewRequest;
import com.qlish.qlish_api.request.NewQuestionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    Page<QuestionDto> getQuestionsBySubjectAndCriteria(AdminQuestionViewRequest request, Pageable pageable);

     QuestionDto updateQuestion(QuestionDto request);

     QuestionDto getQuestion(String id);

     void deleteQuestion(String id);

     Question saveQuestion(Question question);

     QuestionDto addNewQuestion(NewQuestionRequest request);
}
