package com.qlish.qlish_api.enums.math;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MathClass {
    BASIC_MATH("basic-math"),
    ALGEBRA("algebra"),
    GEOMETRY("geometry"),
    TRIGONOMETRY("trigonometry"),
    CALCULUS("calculus"),
    STATISTICS_AND_PROBABILITY("statistics-and-probability"),
    LINEAR_ALGEBRA("linear-algebra"),
    DISCRETE_MATH("discrete-math");

    private final String className;

    public static MathClass fromClassName(String className) {
        for (MathClass item : MathClass.values()) {
            if (item.getClassName().equalsIgnoreCase(className)) {
                return item;
            }
        }
        return null;
    }
}
