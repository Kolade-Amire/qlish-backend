package com.qlish.qlish_api.practice_test.english_test;

import com.qlish.qlish_api.english_question.EnglishQuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class EnglishQuestionMapper {

    public static Page<EnglishQuestionDto> mapQuestionPageToDto(Page<EnglishQuestionEntity> questions){
        var result = questions.stream()
                .map(EnglishQuestionMapper::mapQuestionToDto)
                .toList();
        return new PageImpl<>(result, PageRequest.of(questions.getNumber(), questions.getSize()), questions.getTotalElements());

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
