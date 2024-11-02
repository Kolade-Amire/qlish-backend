package com.qlish.qlish_api.generativeAI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qlish.qlish_api.entity.CustomQuestion;
import com.qlish.qlish_api.request.TestRequest;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface TestHandler {

    String getPrompt(TestRequest request) throws BadRequestException;
    String getSystemInstruction();

    List<CustomQuestion> parseQuestions(String jsonResponse) throws JsonProcessingException;


}
