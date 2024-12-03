package com.qlish.qlish_api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionLevel {
    ADVANCED("advanced"),
    INTERMEDIATE("intermediate"),
    ELEMENTARY("elementary"),
    RANDOM("random");

    private final String levelName;

    public static QuestionLevel fromLevelName(String levelName) {
        for (QuestionLevel level : QuestionLevel.values()) {
            if (level.getLevelName().equalsIgnoreCase(levelName)) {
                return level;
            }
        }
        return null;
    }
}
