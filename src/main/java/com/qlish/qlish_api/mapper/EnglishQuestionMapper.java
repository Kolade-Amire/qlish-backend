package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.EnglishQuestionDto;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;

import java.util.List;

public class EnglishQuestionMapper {

    public static List<EnglishQuestionDto> mapQuestionListToDto(List<EnglishQuestionEntity> questions){
        return questions.stream()
                .map(EnglishQuestionMapper::mapQuestionToDto)
                .toList();

    }

    public static EnglishQuestionDto mapQuestionToDto(EnglishQuestionEntity question){

        return EnglishQuestionDto.builder()
                .id(question.get_id())
                .question(question.getQuestion())
                .options(question.getOptions())
                .questionClass(question.getQuestionClass())
                .questionLevel(question.getQuestionLevel())
                .questionTopic(question.getQuestionTopic())
                .build();
    }

}
