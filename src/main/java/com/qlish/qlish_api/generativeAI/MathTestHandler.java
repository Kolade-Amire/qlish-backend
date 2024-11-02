package com.qlish.qlish_api.generativeAI;

import com.qlish.qlish_api.entity.CustomQuestion;
import com.qlish.qlish_api.request.TestRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("mathTestHandler")
public class MathTestHandler implements TestHandler {
    @Override
    public String getPrompt(TestRequest request) throws BadRequestException {
        return "";
    }

    @Override
    public String getSystemInstruction() {
        return "";
    }

    @Override
    public List<CustomQuestion> parseQuestions(String jsonResponse) {
        return List.of();
    }
}
