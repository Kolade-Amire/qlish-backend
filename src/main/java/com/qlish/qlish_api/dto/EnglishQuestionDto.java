package com.qlish.qlish_api.dto;

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
}
