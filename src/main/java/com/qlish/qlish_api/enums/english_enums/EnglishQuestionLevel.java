package com.qlish.qlish_api.enums.english_enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnglishQuestionLevel {
    ADVANCED("advanced"),
    INTERMEDIATE("intermediate"),
    ELEMENTARY("elementary");

    private final String levelName;

    public static EnglishQuestionLevel fromLevelName(String levelName) {
        for (EnglishQuestionLevel level : EnglishQuestionLevel.values()) {
            if (level.getLevelName().equalsIgnoreCase(levelName)) {
                return level;
            }
        }
        return null;
    }
}
