package com.qlish.qlish_api.enums;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PointsSystem {

     private static int calculatePoints(int scorePercentage) {
        Map<Integer, Integer> pointsMapping = Map.of(
                0, 5,
                10, 10,
                20, 15,
                30, 20,
                40, 30,
                50, 40,
                60, 50,
                70, 60,
                80, 70,
                90, 80
        );

        AtomicInteger gradedPoints = new AtomicInteger();

        pointsMapping.forEach((key, value) -> {
            if (scorePercentage >= key && scorePercentage < key + 10) {
                gradedPoints.set(value);
            }
        });

        return gradedPoints.get();
    }

    public static int getTotalPoints(int scorePercentage, DifficultyLevel difficultyLevel){
         var gradedPoints = calculatePoints(scorePercentage);
        Map<DifficultyLevel, Integer> multiplierMapping = Map.of(
                DifficultyLevel.ELEMENTARY, 1,
                DifficultyLevel.INTERMEDIATE, 2,
                DifficultyLevel.ADVANCED, 3,
                DifficultyLevel.RANDOM, 2
        );
        return (multiplierMapping.get(difficultyLevel) * gradedPoints);
    }
}
