package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.dto.QuestionDto;
import com.qlish.qlish_api.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionMapperFactory {

    QuestionDto mapQuestionToQuestionDto(Question question);

    Page<QuestionDto> mapToQuestionDtoPage(Page<? extends Question> questions, Pageable pageable);

    Question mapQuestionDtoToQuestion(QuestionDto questionDto);
}
