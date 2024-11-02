package com.qlish.qlish_api.dto;


import lombok.*;
import org.bson.types.ObjectId;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
@Setter
@Builder
public class TestQuestionDto {
    private ObjectId id;
    private String questionText;
    private Map<String, String> options;
}
