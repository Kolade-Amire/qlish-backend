package com.qlish.qlish_api.practice_test.english_test;

import com.qlish.qlish_api.english_question.EnglishQuestionClass;
import com.qlish.qlish_api.english_question.EnglishQuestionLevel;
import com.qlish.qlish_api.english_question.EnglishQuestionTopic;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Map;

@RequiredArgsConstructor
@Data
@Builder
public class EnglishQuestionDto {
    private ObjectId id;
    private String question;
    private Map<String, String> options;
    private EnglishQuestionClass questionClass;
    private EnglishQuestionLevel questionLevel;
    private EnglishQuestionTopic questionTopic;
}
