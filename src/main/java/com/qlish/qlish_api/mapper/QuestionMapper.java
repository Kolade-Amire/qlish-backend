package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.dto.CompletedTestQuestionDto;

import java.util.List;

public class QuestionMapper {

    public static List<TestQuestionDto> mapQuestionListToViewDto(List<CompletedTestQuestionDto> questions){
        return questions.stream()
                .map(QuestionMapper::mapQuestionToViewDto)
                .toList();
    }

    public static List<CompletedTestQuestionDto> mapQuestionListToTestDto(List<? extends Question> questions) {
        return questions.stream().map(
                QuestionMapper::mapQuestionToTestDto
        ).toList();
    }


    public static TestQuestionDto mapQuestionToViewDto(CompletedTestQuestionDto question){

        return TestQuestionDto.builder()
                .id(question.get_id())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .build();
    }


    public static CompletedTestQuestionDto mapQuestionToTestDto(Question question){
        return CompletedTestQuestionDto.builder()
                ._id(question.getId())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .correctAnswer(question.getAnswer())
                .build();
    }

}
