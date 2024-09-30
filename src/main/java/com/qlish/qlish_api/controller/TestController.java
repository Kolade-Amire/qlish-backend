package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.dto.TestDto;
import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.request.TestRequest;
import com.qlish.qlish_api.request.TestSubmissionRequest;
import com.qlish.qlish_api.entity.TestResult;
import com.qlish.qlish_api.service.TestService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/tests")
public class TestController {

    private final TestService testService;

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> getTest(@PathVariable ObjectId id) {
        var test = testService.getTestForView(id);
        return ResponseEntity.ok().body(test);
    }

    @PostMapping("/new")
    public ResponseEntity<ObjectId> createTest(@RequestBody TestRequest testRequest) {
        var testId = testService.createTest(testRequest);
        return ResponseEntity.ok().body(testId);
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<Page<TestQuestionDto>> startTest(@PathVariable ObjectId id, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var test = testService.getTestQuestions(id, pageable);
        return ResponseEntity.ok(test);
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<ObjectId> submitTest(@PathVariable ObjectId id, @RequestBody TestSubmissionRequest request) {
        var testId = testService.submitTest(id, request);
        return ResponseEntity.ok().body(testId);
    }

    @GetMapping("{id}/result")
    public ResponseEntity<TestResult> getTestResult(@PathVariable ObjectId id) {
        var result = testService.getTestResult(id);
        return ResponseEntity.ok().body(result);
    }


}
