package com.qlish.qlish_api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;


@AllArgsConstructor
@Getter
public class QuestionRequest {
    private String questionText;
    private Map<String, String> options;
    private String answer;
    private String subject;
    private Map<String, String> modifiers;
}
