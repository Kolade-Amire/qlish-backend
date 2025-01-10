package com.qlish.qlish_api.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qlish.qlish_api.model.Question;
import com.qlish.qlish_api.request.TestRequest;

import java.util.List;
import java.util.Map;

public class QuizHandler implements Handler {
    @Override
    public String getPrompt(TestRequest request) {
        return "";
    }

    @Override
    public String getSystemInstruction() {
        return "";
    }

    @Override
    public boolean validateRequest(String subject, Map<String, String> modifiers) {
        return false;
    }

    @Override
    public List<Question> parseJsonQuestions(String jsonResponse) throws JsonProcessingException {
        return List.of();
    }
}
