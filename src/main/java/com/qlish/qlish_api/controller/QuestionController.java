package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.request.AdminQuestionViewRequest;
import com.qlish.qlish_api.request.NewQuestionRequest;
import com.qlish.qlish_api.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/questions")
public class QuestionController {
    private final QuestionService questionService;

    @PreAuthorize("hasAuthority('ADMIN_READ')")
    @GetMapping("/{subject}")
    public ResponseEntity<Page<QuestionDto>> getQuestionsByCriteria(@RequestBody AdminQuestionViewRequest request, Pageable pageable) {
        Page<QuestionDto> questionsPage = questionService.getQuestionsBySubjectAndCriteria(request, pageable);
        return ResponseEntity.ok(questionsPage);
    }

    @PreAuthorize("hasAuthority('ADMIN_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable String id) {
        var question = questionService.getQuestion(id);
        return ResponseEntity.ok(question);
    }

    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    @PostMapping("/create")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody NewQuestionRequest request) {
        QuestionDto question = questionService.addNewQuestion(request);
        URI newQuestionLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(AppConstants.BASE_URL + "/questions/{id}")
                .buildAndExpand(question.getId())
                .toUri();
        return ResponseEntity.created(newQuestionLocation).body(question);
    }

    @PreAuthorize("hasAuthority('ADMIN_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@RequestBody QuestionDto request) {
        QuestionDto question = questionService.updateQuestion(request);
        return ResponseEntity.ok(question);
    }

}
