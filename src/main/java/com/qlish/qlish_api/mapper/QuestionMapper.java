package com.qlish.qlish_api.mapper;

import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.dto.QuestionRequest;
import com.qlish.qlish_api.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionMapper<T extends Question> {

    QuestionDto mapQuestionToQuestionDto(T question);

    Page<QuestionDto> mapToQuestionDtoPage(Page<T> questions, Pageable pageable);

    T mapQuestionRequestToQuestion(QuestionRequest questionDto);
}
