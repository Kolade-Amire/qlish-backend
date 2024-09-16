package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.QuestionViewDto;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.TestQuestionDto;

import java.util.List;

public class QuestionMapper {

    public static List<QuestionViewDto> mapQuestionListToViewDto(List<TestQuestionDto> questions){
        return questions.stream()
                .map(QuestionMapper::mapQuestionToViewDto)
                .toList();
    }

    public static List<TestQuestionDto> mapQuestionListToTestDto(List<EnglishQuestionEntity> questions) {
        return questions.stream().map(
                QuestionMapper::mapQuestionToTestDto
        ).toList();
    }


    public static QuestionViewDto mapQuestionToViewDto(TestQuestionDto question){

        return QuestionViewDto.builder()
                .id(question.get_id())
                .question(question.getQuestionText())
                .options(question.getOptions())
                .build();
    }


    public static TestQuestionDto mapQuestionToTestDto(EnglishQuestionEntity question){
        return TestQuestionDto.builder()
                ._id(question.get_id())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .correctAnswer(question.getAnswer())
                .build();
    }

}
