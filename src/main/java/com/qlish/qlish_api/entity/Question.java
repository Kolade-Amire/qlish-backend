package com.qlish.qlish_api.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public abstract class Question {
    private String questionText;
    private Map<String, String> options;
    private String answer;
}
