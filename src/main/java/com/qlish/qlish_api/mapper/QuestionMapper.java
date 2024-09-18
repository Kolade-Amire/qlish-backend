package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.QuestionViewDto;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.TestQuestionDto;

import java.util.List;

public class QuestionMapper {

    public static List<QuestionViewDto> mapQuestionListToViewDto(List<TestQuestionDto> questions){
        return questions.stream()
                .map(QuestionMapper::mapQuestionToViewDto)
                .toList();
    }

    public static List<TestQuestionDto> mapQuestionListToTestDto(List<? extends Question> questions) {
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


    public static TestQuestionDto mapQuestionToTestDto(Question question){
        return TestQuestionDto.builder()
                ._id(question.getId())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .correctAnswer(question.getAnswer())
                .build();
    }

}
