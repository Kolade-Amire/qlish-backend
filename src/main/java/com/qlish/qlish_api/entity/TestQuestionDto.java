package com.qlish.qlish_api.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Map;

@Data
@Builder
public class TestQuestionDto {
    private ObjectId _id;
    private String questionText;
    private Map<String, String> options;
    private String correctAnswer;
    private String selectedOption;
    private boolean isAnswerCorrect;
}
