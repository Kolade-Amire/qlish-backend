package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.entity.CompletedTestQuestion;
import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.entity.Question;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TestQuestionMapper {

    public static List<TestQuestionDto> mapQuestionListToTestViewDto(List<CompletedTestQuestion> questions){
        return questions.stream()
                .map(TestQuestionMapper::mapQuestionToTestViewDto)
                .toList();
    }

    public static List<CompletedTestQuestion> mapQuestionListToSavedTestQuestionDto(List<? extends Question> questions) {
        return questions.stream().map(
                TestQuestionMapper::mapQuestionToSavedTestQuestionDto
        ).toList();
    }

    public static TestQuestionDto mapQuestionToTestViewDto(CompletedTestQuestion question){

        return TestQuestionDto.builder()
                .id(question.get_id().toHexString())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .build();
    }


    public static CompletedTestQuestion mapQuestionToSavedTestQuestionDto(Question question){
        return CompletedTestQuestion.builder()
                ._id(question.getId())
                .questionText(question.getQuestionText())
                .options(question.getOptions())
                .correctAnswer(question.getAnswer())
                .build();
    }

}
