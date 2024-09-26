package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.CompletedTestQuestionDto;
import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.dto.TestQuestionDto;
import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.factory.QuestionRepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class TestQuestionMapper {

    private final QuestionRepositoryFactory factory;

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

//    public static Page<QuestionDto> mapEnglishQuestionToQuestionDtoPage(Page<EnglishQuestionEntity> questions, Pageable pageable) {
//        List<QuestionDto> questionDtos = questions.stream().map(
//                TestQuestionMapper::mapEnglishQuestionToQuestionDto
//        ).toList();
//
//        return new PageImpl<>(questionDtos, pageable, questions.getTotalElements());
//    }


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
