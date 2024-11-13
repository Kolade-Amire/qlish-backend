package com.qlish.qlish_api.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.request.TestRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("chemistryHandler")
public class ChemistryHandler implements Handler{
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
