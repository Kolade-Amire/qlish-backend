package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.EnglishQuestionViewDto;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.EnglishQuestionTestDto;

import java.util.List;

public class EnglishQuestionMapper {

    public static List<EnglishQuestionViewDto> mapQuestionListToViewDto(List<EnglishQuestionTestDto> questions){
        return questions.stream()
                .map(EnglishQuestionMapper::mapQuestionToViewDto)
                .toList();
    }

    public static List<EnglishQuestionTestDto> mapQuestionListToTestDto(List<EnglishQuestionEntity> questions) {
        return questions.stream().map(
                EnglishQuestionMapper::mapQuestionToTestDto
        ).toList();
    }


    public static EnglishQuestionViewDto mapQuestionToViewDto(EnglishQuestionTestDto question){

        return EnglishQuestionViewDto.builder()
                .id(question.get_id())
                .question(question.getQuestionText())
                .options(question.getOptions())
                .build();
    }


    public static EnglishQuestionTestDto mapQuestionToTestDto(EnglishQuestionEntity question){
        return EnglishQuestionTestDto.builder()
                ._id(question.get_id())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .correctAnswer(question.getAnswer())
                .build();
    }

}
