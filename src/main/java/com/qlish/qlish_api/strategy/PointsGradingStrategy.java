package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.enums.QuestionLevel;

import java.util.function.BiFunction;

@FunctionalInterface
public interface PointsGradingStrategy extends BiFunction<Integer, QuestionLevel, Integer> {

    static PointsGradingStrategy calculateTestPoints(){
        return (integer, questionLevel) -> {

        }
    }

}
