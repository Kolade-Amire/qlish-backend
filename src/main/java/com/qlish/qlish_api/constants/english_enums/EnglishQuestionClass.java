package com.qlish.qlish_api.constants.english_enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnglishQuestionClass {

    GRAMMAR("grammar"),
    VOCABULARY("vocabulary");

    private final String className;

    public static EnglishQuestionClass fromClassName(String className) {
        for (EnglishQuestionClass item : EnglishQuestionClass.values()) {
            if (item.getClassName().equalsIgnoreCase(className)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Invalid class name: " + className);
    }

}
