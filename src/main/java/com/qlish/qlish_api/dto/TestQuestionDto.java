package com.qlish.qlish_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionDto {
    private ObjectId _id;
    private String questionText;
    private Map<String, String> options;
    private String correctAnswer;
    private String selectedOption;
    private boolean isAnswerCorrect;
}
