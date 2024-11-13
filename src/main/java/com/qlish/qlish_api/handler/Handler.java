package com.qlish.qlish_api.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.request.TestRequest;

import java.util.List;
import java.util.Map;

public interface Handler {

    String getPrompt(TestRequest request);

    String getSystemInstruction();

    boolean validateRequest(String subject, Map<String, String> modifiers);

    List<Question> parseJsonQuestions(String jsonResponse) throws JsonProcessingException;

}
