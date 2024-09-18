package com.qlish.qlish_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnglishModifier extends QuestionModifier{
    @JsonProperty("class")
    private String questionClass;
    private String topic;
}
