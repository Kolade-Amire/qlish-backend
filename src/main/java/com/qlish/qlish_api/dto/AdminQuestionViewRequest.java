package com.qlish.qlish_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class AdminQuestionViewRequest {
    private Map<String, String> modifiers;
    private String subject;
}
