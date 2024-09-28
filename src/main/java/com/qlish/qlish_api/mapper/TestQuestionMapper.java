package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.CompletedTestQuestionDto;
import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.factory.QuestionFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TestQuestionMapper {

    private final QuestionFactory factory;

    public static List<TestQuestionDto> mapQuestionListToTestViewDto(List<CompletedTestQuestionDto> questions){
        return questions.stream()
                .map(TestQuestionMapper::mapQuestionToTestViewDto)
                .toList();
    }

    public static List<CompletedTestQuestionDto> mapQuestionListToSavedTestQuestionDto(List<? extends Question> questions) {
        return questions.stream().map(
                TestQuestionMapper::mapQuestionToSavedTestQuestionDto
        ).toList();
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

}
