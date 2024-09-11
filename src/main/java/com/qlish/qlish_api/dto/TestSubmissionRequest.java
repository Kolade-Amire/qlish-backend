package com.qlish.qlish_api.dto;

import org.bson.types.ObjectId;

import java.util.List;

public class TestSubmissionRequest {
    private ObjectId id;
    private ObjectId userId;
    private String subject;
    private List<QuestionSubmission> questionSubmissions;

}

