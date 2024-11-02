package com.qlish.qlish_api.entity;

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
public class TestQuestion {
    private ObjectId id;
    private String questionText;
    private Map<String, String> options;
    private Map<String, String> modifiers;
    private String correctAnswer;
    private String selectedOption;
    private boolean isAnswerCorrect;
}
