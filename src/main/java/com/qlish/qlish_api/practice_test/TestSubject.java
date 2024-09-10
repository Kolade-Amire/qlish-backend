package com.qlish.qlish_api.practice_test;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TestSubject {
    ENGLISH("English"),
    MATH("Math");

    private final String subjectName;
}

