package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.CompletedTestQuestionDto;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.Question;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static List<TestQuestionDto> mapQuestionListToTestViewDto(List<CompletedTestQuestionDto> questions){
        return questions.stream()
                .map(QuestionMapper::mapQuestionToTestViewDto)
                .toList();
    }

    public static List<CompletedTestQuestionDto> mapQuestionListToSavedTestQuestionDto(List<? extends Question> questions) {
        return questions.stream().map(
                QuestionMapper::mapQuestionToSavedTestQuestionDto
        ).toList();
    }

    public static Page<QuestionDto> mapQuestionToQuestionDto(Page<Question> questions) {

        return questions.stream().map(
                QuestionMapper::mapEnglishQuestionToQuestionDto
        ).collect(Collectors.toCollection());
    }


    public static TestQuestionDto mapQuestionToTestViewDto(CompletedTestQuestionDto question){

        return TestQuestionDto.builder()
                .id(question.get_id())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .build();
    }


    public static CompletedTestQuestionDto mapQuestionToSavedTestQuestionDto(Question question){
        return CompletedTestQuestionDto.builder()
                ._id(question.getId())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .correctAnswer(question.getAnswer())
                .build();
    }
    //TODO: refactor to use modifier factory
    public <Q extends Question> static QuestionDto mapEnglishQuestionToQuestionDto(EnglishQuestionEntity question){

        var modifier = new EnglishModifier(
                question.getQuestionLevel(),
                question.getQuestionClass(),
                question.getQuestionTopic()
        );
        return QuestionDto.builder()
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .answer(question.getAnswer())
                .modifier(modifier)
                .build();
    }

}
