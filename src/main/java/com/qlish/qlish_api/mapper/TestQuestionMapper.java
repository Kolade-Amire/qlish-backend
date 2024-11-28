package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.model.Question;
import com.qlish.qlish_api.model.TestQuestion;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TestQuestionMapper {

    public static List<TestQuestionDto> mapQuestionListToTestViewDto(List<TestQuestion> questions){
        return questions.stream()
                .map(TestQuestionMapper::mapQuestionToTestViewDto)
                .toList();
    }

    public static List<TestQuestion> mapQuestionListToSavedTestQuestionDto(List<Question> questions) {
        return questions.stream().map(
                TestQuestionMapper::mapQuestionToSavedTestQuestionDto
        ).toList();
    }

    public static TestQuestionDto mapQuestionToTestViewDto(TestQuestion question){

        return TestQuestionDto.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .build();
    }


    public static TestQuestion mapQuestionToSavedTestQuestionDto(Question question){
        return TestQuestion.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .modifiers(question.getModifiers())
                .correctAnswer(question.getCorrectAnswer())
                .build();
    }

}
