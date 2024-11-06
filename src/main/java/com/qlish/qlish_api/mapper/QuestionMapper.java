package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.entity.CustomQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;


public class QuestionMapper {


    public static QuestionDto mapQuestionToQuestionDto(CustomQuestion question) {

        return QuestionDto.builder()
                .id(question.getId().toHexString())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .answer(question.getCorrectAnswer())
                .modifiers(question.getModifiers())
                .subject(question.getSubject())
                .build();
    }


    public static Page<QuestionDto> mapToQuestionDtoPage(Page<CustomQuestion> questions, Pageable pageable) {
        List<QuestionDto> questionDtos = questions.stream().map(
                QuestionMapper::mapQuestionToQuestionDto
        ).toList();

        return new PageImpl<>(questionDtos, pageable, questions.getTotalElements());
    }


}

