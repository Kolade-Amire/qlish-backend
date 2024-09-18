package com.qlish.qlish_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Question {
    private String questionText;
    private Map<String, String> options;
    private String answer;
}
