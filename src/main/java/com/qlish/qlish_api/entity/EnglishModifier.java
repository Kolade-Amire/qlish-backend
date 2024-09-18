package com.qlish.qlish_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EnglishModifier extends QuestionModifier{
    @JsonProperty("class")
    private String questionClass;
    private String topic;

    public EnglishModifier(String level, String questionClass, String topic) {
        super(level);
        this.questionClass = questionClass;
        this.topic = topic;
    }
}
