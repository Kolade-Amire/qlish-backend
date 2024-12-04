package com.qlish.qlish_api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DifficultyLevel {
    ADVANCED("advanced"),
    INTERMEDIATE("intermediate"),
    ELEMENTARY("elementary"),
    RANDOM("random");

    private final String levelName;

    public static DifficultyLevel fromLevelName(String levelName) {
        for (DifficultyLevel level : DifficultyLevel.values()) {
            if (level.getLevelName().equalsIgnoreCase(levelName)) {
                return level;
            }
        }
        return null;
    }
}
