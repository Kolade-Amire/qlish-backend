package com.qlish.qlish_api.enums.math;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MathTopic {
    BASIC_MATH("basic-math"),
    ALGEBRA("algebra"),
    GEOMETRY("geometry"),
    TRIGONOMETRY("trigonometry"),
    CALCULUS("calculus"),
    STATISTICS_AND_PROBABILITY("statistics-prob"),
    LINEAR_ALGEBRA("linear-algebra"),
    DISCRETE_MATH("discrete-math");

    private final String className;

    public static MathTopic fromClassName(String className) {
        for (MathTopic item : MathTopic.values()) {
            if (item.getClassName().equalsIgnoreCase(className)) {
                return item;
            }
        }
        return null;
    }
}
