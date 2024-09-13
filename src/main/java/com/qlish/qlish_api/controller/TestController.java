package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.service.EnglishTestService;
import com.qlish.qlish_api.dto.TestSubmissionRequest;
import com.qlish.qlish_api.service.EnglishQuestionService;
import com.qlish.qlish_api.dto.EnglishQuestionDto;
import com.qlish.qlish_api.dto.EnglishTestRequest;
import com.qlish.qlish_api.entity.TestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final EnglishTestService englishTestService;
    private final EnglishQuestionService englishQuestionService;


    @PostMapping("english/new")
    public ResponseEntity<Page<EnglishQuestionDto>> getEnglishQuestions(@RequestBody EnglishTestRequest englishTestRequest, @RequestParam int pageNumber, @RequestParam int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        var questionList = englishTestService.initiateNewTest(englishTestRequest, page);

        return null;
    }

    @PostMapping
    public ResponseEntity<TestResult> submitTest (@RequestBody TestSubmissionRequest submissionRequest) {
        return null;
    }


}
