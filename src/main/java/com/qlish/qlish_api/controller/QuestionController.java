package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/questions")
public class QuestionController {
    private final QuestionService questionService;


}
