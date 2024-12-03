package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.enums.PointsSystem;
import com.qlish.qlish_api.enums.QuestionLevel;

import java.util.function.BiFunction;

@FunctionalInterface
public interface PointsGradingStrategy extends BiFunction<Integer, QuestionLevel, Integer> {

    static PointsGradingStrategy calculateTestPoints(){
        return PointsSystem::getTotalPoints;
    }

    static PointsGradingStrategy calculateQuizPoints(){
        return (integer, questionLevel) -> {
            return PointsSystem.getTotalPoints(integer);
        };
    }

}
