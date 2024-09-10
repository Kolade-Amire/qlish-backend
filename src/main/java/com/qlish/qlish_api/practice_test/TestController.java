package com.qlish.qlish_api.practice_test;

import com.qlish.qlish_api.english_question.EnglishQuestionEntity;
import com.qlish.qlish_api.english_question.EnglishQuestionService;
import com.qlish.qlish_api.practice_test.english_test.EnglishQuestionDto;
import com.qlish.qlish_api.practice_test.english_test.EnglishTestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;
    private final EnglishQuestionService englishQuestionService;


    @PostMapping("english/new")
    public ResponseEntity<Page<EnglishQuestionDto>> getEnglishQuestions(@RequestBody EnglishTestRequest englishTestRequest, @RequestParam int pageNumber, @RequestParam int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        var questionList = testService.startNewEnglishTest(englishTestRequest, page);

        return ResponseEntity.ok(questionList);

    }

    @PostMapping
    public ResponseEntity<TestResult> submitTest (@RequestBody TestSubmissionRequest submissionRequest) {
        return null;
    }


}
