//package com.qlish.qlish_api.handler;
//
//import com.qlish.qlish_api.entity.Question;
//import com.qlish.qlish_api.request.NewQuestionRequest;
//import com.qlish.qlish_api.request.TestRequest;
//import org.apache.coyote.BadRequestException;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//
//@Component("mathHandler")
//public class MathHandler implements Handler {
//    @Override
//    public String getPrompt(TestRequest request) throws BadRequestException {
//        return "";
//    }
//
//    @Override
//    public String getSystemInstruction() {
//        return "";
//    }
//
//    @Override
//    public boolean validateRequest(String subject, Map<String, String> modifiers) {
//        return false;
//    }
//
//    @Override
//    public List<Question> parseJsonQuestions(String jsonResponse) {
//        return List.of();
//    }
//}
