package com.qlish.qlish_api.request;

import com.qlish.qlish_api.enums.TestSubject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;


@AllArgsConstructor
@Getter
public class NewQuestionRequest {
    private String questionText;
    private Map<String, String> options;
    private String answer;
    private TestSubject subject;
    private Map<String, String> modifiers;
}
