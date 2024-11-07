package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.TestDto;
import com.qlish.qlish_api.entity.TestEntity;

public class TestMapper {

    public static TestDto mapTestToDto(TestEntity test) {
        return TestDto.builder()
                .id(test.getId().toHexString())
                .userId(test.getTestDetails().getUserId())
                .questions(test.getQuestions())
                .testSubject(test.getTestDetails().getTestSubject().getDisplayName())
                .startedAt(test.getTestDetails().getStartedAt())
                .totalQuestionCount(test.getTestDetails().getTotalQuestionCount())
                .totalCorrectAnswers(test.getTestDetails().getTotalCorrectAnswers())
                .totalIncorrectAnswers(test.getTestDetails().getTotalIncorrectAnswers())
                .scorePercentage(test.getTestDetails().getScorePercentage())
                .build();
    }
}
