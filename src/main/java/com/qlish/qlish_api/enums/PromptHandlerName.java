package com.qlish.qlish_api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PromptHandlerName {
    ENGLISH("englishTestHandler"),
    MATHEMATICS("mathTestHandler"),
    PHYSICS("physicsTestHandler"),
    CHEMISTRY("chemistryTestHandler"),
    HISTORY("historyTestHandler"),
    GENERAL_QUIZ("quizTestHandler");

    private final String handlerName;

    public static String getHandlerNameBySubject(TestSubject subjectName) {
        return Arrays.stream(PromptHandlerName.values())
                .filter(handlerEnumValue -> handlerEnumValue.name().equalsIgnoreCase(subjectName.name()))
                .findFirst()
                .map(PromptHandlerName::getHandlerName)
                .orElseThrow(() -> new IllegalArgumentException("No handler found for subject: " + subjectName.name()));
    }
}
