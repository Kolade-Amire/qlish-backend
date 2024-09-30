package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.request.AdminQuestionViewRequest;
import com.qlish.qlish_api.request.QuestionRequest;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.request.UpdateQuestionRequest;
import com.qlish.qlish_api.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
        Page<QuestionDto> questionsPage = questionService.getQuestionsByCriteria(request, pageable);
        return ResponseEntity.ok(questionsPage);
    }

    @PreAuthorize("hasAuthority('ADMIN_READ')")
    @GetMapping("/{subject}/{id}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable ObjectId id, @PathVariable String subject) {
        var question = questionService.getQuestion(id, subject);
        return ResponseEntity.ok(question);
    }

    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    @PostMapping("/{subject}/new")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionRequest request) {
        QuestionDto question = questionService.addNewQuestion(request);
        var subject = TestSubject.getSubjectByDisplayName(request.getSubject());
        URI newQuestionLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(AppConstants.BASE_URL + "/questions/{subject}/{id}")
                .buildAndExpand(subject.getDisplayName().toLowerCase(), question.getId())
                .toUri();
        return ResponseEntity.created(newQuestionLocation).body(question);
    }

    @PreAuthorize("hasAuthority('ADMIN_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable ObjectId id, @RequestBody String subject) {
        questionService.deleteQuestion(id, subject);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN_UPDATE')")
    @PutMapping("{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable ObjectId id, @RequestBody UpdateQuestionRequest request) {
        QuestionDto question = questionService.updateQuestion(id, request);
        return ResponseEntity.ok(question);
    }

}
