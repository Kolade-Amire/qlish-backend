package com.qlish.qlish_api.practice_test;

import com.qlish.qlish_api.english_question.EnglishQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;
    private final EnglishQuestionService englishQuestionService;


}
