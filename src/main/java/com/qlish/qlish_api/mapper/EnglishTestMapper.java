package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.EnglishTestDto;
import com.qlish.qlish_api.entity.EnglishTest;

public class EnglishTestMapper {

    public static EnglishTestDto mapTestToDto (EnglishTest test) {

        return EnglishTestDto.builder()
                .id(test.get_id())
                .userId(test.getTestDetails().getUserId())
                .testSubject(test.getTestDetails().getTestSubject().getDisplayName())
                .startedAt(test.getTestDetails().getStartedAt())
                .totalQuestionCount(test.getTestDetails().getTotalQuestionCount())
                .totalCorrectAnswers(test.getTestDetails().getTotalCorrectAnswers())
                .totalIncorrectAnswers(test.getTestDetails().getTotalIncorrectAnswers())
                .testStatus(test.getTestStatus())
                .isCompleted(test.getTestDetails().isCompleted())
                .build();
    }
}
