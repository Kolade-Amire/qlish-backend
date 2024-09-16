package com.qlish.qlish_api.dto;

import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
public class TestSubmissionRequest {
    private ObjectId id;
    private List<QuestionSubmissionRequest> questionSubmissionRequests;

}

