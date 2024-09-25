package com.qlish.qlish_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TestSubmissionRequest {
    private List<TestQuestionSubmissionRequest> answers;
}

