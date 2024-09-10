package com.qlish.qlish_api.practice_test;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
public class TestResult {
    private ObjectId id;
    private ObjectId userId;
    private int totalQuestions;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private List<QuestionFeedback> questionFeedback;
}
