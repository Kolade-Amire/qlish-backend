package com.qlish.qlish_api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum TestSubject {
    ENGLISH("english"),
    MATH("math"),
    PHYSICS("physics"),
    CHEMISTRY("chemistry"),
    BIOLOGY("biology"),
    HISTORY("history"),
    QUIZ("quiz");

    private final String displayName;

    public static TestSubject getSubjectByDisplayName(String name) {
        return Arrays.stream(TestSubject.values())
                .filter(subject -> subject.getDisplayName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No subject found with name: " + name));
    }
}

