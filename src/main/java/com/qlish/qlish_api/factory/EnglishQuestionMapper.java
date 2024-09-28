package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.dto.QuestionDto;
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
public class EnglishQuestionMapper implements QuestionMapper<EnglishQuestionEntity> {

    @Override
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
                .modifier(modifier)
                .build();
    }



    @Override
    public Page<QuestionDto> mapToQuestionDtoPage(Page<EnglishQuestionEntity> questions, Pageable pageable) {
        List<QuestionDto> questionDtos = questions.stream().map(
                this::mapQuestionToQuestionDto
        ).toList();

        return new PageImpl<>(questionDtos, pageable, questions.getTotalElements());
    }

    @Override
    public EnglishQuestionEntity mapQuestionDtoToQuestion(QuestionDto questionDto) {
        return new EnglishQuestionEntity(
                questionDto.getId(),
                questionDto.getQuestionText(),
                questionDto.getOptions(),
                questionDto.getAnswer(),
                questionDto.getModifier().get("class"),
                questionDto.getModifier().get("level"),
                questionDto.getModifier().get("topic")
        );
    }


}

