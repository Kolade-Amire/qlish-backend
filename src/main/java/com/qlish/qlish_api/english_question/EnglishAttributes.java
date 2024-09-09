package com.qlish.qlish_api.english_question;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EnglishAttributes {
    TOPIC("questionTopic"),
    LEVEL("questionLevel"),
    CLASS("questionClass");

    private final String attributeName;
}
