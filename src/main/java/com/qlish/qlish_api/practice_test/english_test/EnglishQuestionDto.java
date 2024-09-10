package com.qlish.qlish_api.practice_test.english_test;

import com.qlish.qlish_api.english_question.EnglishQuestionClass;
import com.qlish.qlish_api.english_question.EnglishQuestionLevel;
import com.qlish.qlish_api.english_question.EnglishQuestionTopic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Map;

@AllArgsConstructor
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
