package com.qlish.qlish_api.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum TestSubject {
    ENGLISH("English"),
    MATHEMATICS("Math");

    private final String displayName;

    public static boolean isValidSubjectName(String name) {
        return Arrays.stream(TestSubject.values())
                .anyMatch(subject -> subject.getDisplayName().equalsIgnoreCase(name));
    }
}

