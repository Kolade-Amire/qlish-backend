package com.qlish.qlish_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Builder
public class EnglishQuestionDto {
    private ObjectId id;
    private String questionText;
    private Map<String, String> options;
    private String answer;
    private QuestionModifier modifier;
}
