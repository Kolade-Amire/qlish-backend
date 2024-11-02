package com.qlish.qlish_api.generativeAI;

import com.qlish.qlish_api.request.TestRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class Prompts {

    public String getEnglishPrompt(TestRequest request) throws BadRequestException {

        try {
            if (request.isRandom()) {
                return String.format("Generate %d multiple-choice random english questions", request.getCount());
            } else {
                return String.format(
                        "Generate %d multiple-choice %s questions on %s. The questions should be at a(n) %s difficulty level.",
                        request.getCount(),
                        request.getModifiers().get("class"),
                        request.getModifiers().get("topic"),
                        request.getModifiers().get("level")
                );
            }
        } catch (Exception e) {
            throw new BadRequestException("Bad request, try again", e);
        }

    }
}
