package com.qlish.qlish_api.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum TestSubject {
    ENGLISH("English"),
    MATH("Math");

    private final String subjectName;

    public static boolean isValidSubjectName(String name) {
        return Arrays.stream(TestSubject.values())
                .anyMatch(subject -> subject.getSubjectName().equalsIgnoreCase(name));
    }
}

