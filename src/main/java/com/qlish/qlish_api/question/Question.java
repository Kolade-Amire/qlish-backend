package com.qlish.qlish_api.question;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public abstract class Question {
    private String question;
    private Map<String, String> options;
    private String answer;
}
