package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.request.QuestionRequest;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Qualifier("english")
public class EnglishQuestionMapper {


    public QuestionDto mapQuestionToQuestionDto(EnglishQuestionEntity question) {

        var modifier = Map.of(
                "level", question.getQuestionLevel(),
                "class", question.getQuestionClass(),
                "topic", question.getQuestionTopic()
        );

        return QuestionDto.builder()
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .answer(question.getAnswer())
                .modifiers(modifier)
                .subject("English")
                .build();
    }


    public Page<QuestionDto> mapToQuestionDtoPage(Page<EnglishQuestionEntity> questions, Pageable pageable) {
        List<QuestionDto> questionDtos = questions.stream().map(
                this::mapQuestionToQuestionDto
        ).toList();

        return new PageImpl<>(questionDtos, pageable, questions.getTotalElements());
    }


    public EnglishQuestionEntity mapQuestionRequestToQuestion(QuestionRequest request) {
        return new EnglishQuestionEntity(
                null,
                request.getQuestionText(),
                request.getOptions(),
                request.getAnswer(),
                request.getModifiers().get("class"),
                request.getModifiers().get("level"),
                request.getModifiers().get("topic")
        );
    }


}

