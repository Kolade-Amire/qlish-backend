package com.qlish.qlish_api .practice_test;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@Builder
public class QuestionFeedback {
    private ObjectId id;
    private String submittedAnswer;
    private String correctAnswer;
    private boolean isAnswerCorrect;
}
