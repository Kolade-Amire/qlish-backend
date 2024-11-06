package com.qlish.qlish_api.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qlish.qlish_api.entity.CustomQuestion;
import com.qlish.qlish_api.request.NewQuestionRequest;
import com.qlish.qlish_api.request.TestRequest;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface Handler {

    String getPrompt(TestRequest request) throws BadRequestException;
    String getSystemInstruction();
    boolean validateNewQuestionRequest(NewQuestionRequest request);
    List<CustomQuestion> parseJsonQuestions(String jsonResponse) throws JsonProcessingException;


}
