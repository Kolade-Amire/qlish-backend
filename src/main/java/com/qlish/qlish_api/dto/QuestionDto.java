package com.qlish.qlish_api.dto;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
@Setter
@Builder
public class QuestionDto {
    private String id;
    private String questionText;
    private Map<String, String> options;
    private String answer;
    private String subject;
    private Map<String, String> modifiers;

}
