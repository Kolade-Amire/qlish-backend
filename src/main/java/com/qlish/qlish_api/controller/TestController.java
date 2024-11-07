package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.dto.TestDto;
import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.entity.TestResult;
import com.qlish.qlish_api.exception.GenerativeAIException;
import com.qlish.qlish_api.request.TestRequest;
import com.qlish.qlish_api.request.TestSubmissionRequest;
import com.qlish.qlish_api.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> getTest(@PathVariable String id) {
        var test = testService.getTestForView(id);
        return ResponseEntity.ok().body(test);
    }

    @PostMapping("/new")
    public ResponseEntity<String> createTest(@RequestBody TestRequest testRequest) throws GenerativeAIException {
        var testId = testService.createTest(testRequest);
        return ResponseEntity.ok().body(testId);
    }

    @GetMapping("/new/{id}")
    public ResponseEntity<Page<TestQuestionDto>> startTest(@PathVariable String id, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var test = testService.getTestQuestions(id, pageable);
        return ResponseEntity.ok(test);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<String> submitTest(@RequestBody TestSubmissionRequest request) {
        var testId = testService.submitTest(request);
        return ResponseEntity.ok().body(testId);
    }

    @GetMapping("result/{id}")
    public ResponseEntity<TestResult> getTestResult(@PathVariable String id) {
        var result = testService.getTestResult(id);
        return ResponseEntity.ok().body(result);
    }

}
