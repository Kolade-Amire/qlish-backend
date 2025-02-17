package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.dto.TestDto;
import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.model.TestResult;
import com.qlish.qlish_api.exception.GenerativeAIException;
import com.qlish.qlish_api.request.TestRequest;
import com.qlish.qlish_api.request.TestSubmissionRequest;
import com.qlish.qlish_api.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> getTest(@PathVariable String id) {
        var test = testService.getTestForView(id);
        return ResponseEntity.ok(test);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createTest(@RequestBody TestRequest testRequest) throws GenerativeAIException {
        var testId = testService.createTest(testRequest);
        return ResponseEntity.ok(testId);
    }

    @GetMapping("/new/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<TestQuestionDto>> startTest(@PathVariable String id, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var testQuestions = testService.getTestQuestions(id, pageable);
        return ResponseEntity.ok(testQuestions);
    }

    @PostMapping("/submit/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> submitTest(@RequestBody TestSubmissionRequest request) {
        var testId = testService.submitTest(request);
        return ResponseEntity.ok(testId);
    }

    @GetMapping("result/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TestResult> getTestResult(@PathVariable String id) {
        var result = testService.getTestResult(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteTest(@PathVariable String id){
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }


}
