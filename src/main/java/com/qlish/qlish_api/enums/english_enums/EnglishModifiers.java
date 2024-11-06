package com.qlish.qlish_api.enums.english_enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EnglishModifiers {
    TOPIC("topic"),
    LEVEL("level"),
    CLASS("class");

    private final String attributeName;
}
