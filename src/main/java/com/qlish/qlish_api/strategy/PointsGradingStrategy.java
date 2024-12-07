package com.qlish.qlish_api.strategy;

import com.qlish.qlish_api.enums.DifficultyLevel;
import com.qlish.qlish_api.util.PointsSystem;

import java.util.function.BiFunction;

@FunctionalInterface
public interface PointsGradingStrategy extends BiFunction<Integer, DifficultyLevel, Integer> {

    static PointsGradingStrategy calculatePoints(){
        return PointsSystem::getTotalPoints;
    }
}
