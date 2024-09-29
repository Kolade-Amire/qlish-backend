package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.dto.AdminQuestionViewRequest;
import com.qlish.qlish_api.dto.QuestionRequest;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/questions")
public class QuestionController {
    private final QuestionService questionService;

    /*
     *Get all questions under criteria set(s)
     * create new question/update question
     *
     */

    @GetMapping("/{subject}")
    public ResponseEntity<Page<QuestionDto>> getQuestions(@RequestBody AdminQuestionViewRequest request, Pageable pageable) {
        Page<QuestionDto> questionsPage = questionService.getQuestionsByCriteria(request, pageable);
        return ResponseEntity.ok(questionsPage);
    }

    @PostMapping("/{subject}/new")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionRequest request) {
        QuestionDto question = questionService.addNewQuestion(request);
        return ResponseEntity.ok(question);
    }

}
