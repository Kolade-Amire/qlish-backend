package com.qlish.qlish_api.english_question;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnglishQuestionClass {

    GRAMMAR("grammar"),
    VOCABULARY("vocabulary");

    private final String className;

}
