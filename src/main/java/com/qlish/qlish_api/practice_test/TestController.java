package com.qlish.qlish_api.practice_test;

import com.qlish.qlish_api.english_question.EnglishQuestionEntity;
import com.qlish.qlish_api.english_question.EnglishQuestionLevel;
import com.qlish.qlish_api.english_question.EnglishQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;
    private final EnglishQuestionService englishQuestionService;


    @GetMapping("/english")
    public ResponseEntity<Page<EnglishQuestionEntity>> getEnglishQuestions(TestRequest testRequest, Pageable pageable) {

        var modifiers = testRequest.getTestModifier();
        var questionLevel = EnglishQuestionLevel.valueOf(modifiers.getModifier("questionLevel"));
        return englishQuestionService.getEnglishQuestions(pageable, modifiers.getModifier("questionLevel"), modifiers.getModifier("questionClass"), modifiers.getModifier("questionTopic"), testRequest.getQuestionCount());


    }


}
