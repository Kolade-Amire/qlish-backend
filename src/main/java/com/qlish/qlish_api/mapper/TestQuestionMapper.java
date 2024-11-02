package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.entity.CustomQuestion;
import com.qlish.qlish_api.entity.TestQuestion;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TestQuestionMapper {

    public static List<TestQuestionDto> mapQuestionListToTestViewDto(List<TestQuestion> questions){
        return questions.stream()
                .map(TestQuestionMapper::mapQuestionToTestViewDto)
                .toList();
    }

    public static List<TestQuestion> mapQuestionListToSavedTestQuestionDto(List<CustomQuestion> questions) {
        return questions.stream().map(
                TestQuestionMapper::mapQuestionToSavedTestQuestionDto
        ).toList();
    }

    public static TestQuestionDto mapQuestionToTestViewDto(TestQuestion question){

        return TestQuestionDto.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .build();
    }


    public static TestQuestion mapQuestionToSavedTestQuestionDto(CustomQuestion question){
        return TestQuestion.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .modifiers(question.getModifiers())
                .correctAnswer(question.getCorrectAnswer())
                .build();
    }

}
