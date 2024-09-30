package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.request.AdminQuestionViewRequest;
import com.qlish.qlish_api.request.QuestionRequest;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/questions")
public class QuestionController {
    private final QuestionService questionService;

    /*
     *Get all questions under criteria set(s)
     * create new question/update question
     * delete question
     */

    @GetMapping("/{subject}")
    public ResponseEntity<Page<QuestionDto>> getQuestions(@RequestBody AdminQuestionViewRequest request, Pageable pageable) {
        Page<QuestionDto> questionsPage = questionService.getQuestionsByCriteria(request, pageable);
        return ResponseEntity.ok(questionsPage);
    }

    @PostMapping("/{subject}/new")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionRequest request) {
        QuestionDto question = questionService.addNewQuestion(request);
        URI newQuestionLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(question.getId())
                .toUri();
        return ResponseEntity.created(newQuestionLocation).body(question);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable ObjectId id, @RequestBody String subject) {
        questionService.deleteQuestion(id, subject);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable ObjectId id, @RequestBody QuestionRequest request) {

    }

}
