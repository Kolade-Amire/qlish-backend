package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.TestDto;
import com.qlish.qlish_api.model.TestEntity;

public class TestMapper {
    public static TestDto mapTestToDto(TestEntity test) {
        var testDetails = test.getTestDetails();
        return TestDto.builder()
                .id(test.getId().toHexString())
                .userId(testDetails.getUserId())
                .questions(test.getQuestions())
                .testSubject(testDetails.getTestSubject())
                .startedAt(testDetails.getStartedAt())
                .totalQuestionCount(testDetails.getTotalQuestions())
                .totalCorrectAnswers(testDetails.getTotalCorrect())
                .totalIncorrectAnswers(testDetails.getTotalIncorrect())
                .pointEarned(testDetails.getPointsEarned())
                .scorePercentage(testDetails.getScorePercentage())
                .build();
    }
}