package com.qlish.qlish_api.dto;

import com.qlish.qlish_api.constants.english_enums.EnglishQuestionClass;
import com.qlish.qlish_api.constants.english_enums.EnglishQuestionLevel;
import com.qlish.qlish_api.constants.english_enums.EnglishQuestionTopic;
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
