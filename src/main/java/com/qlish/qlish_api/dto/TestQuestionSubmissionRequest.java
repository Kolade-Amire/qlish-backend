package com.qlish.qlish_api.dto;

import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
public class TestQuestionSubmissionRequest {
    private ObjectId questionId;
    private String selectedOption;
}
