package com.qlish.qlish_api.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlish.qlish_api.entity.CustomQuestion;
import com.qlish.qlish_api.exception.CustomQlishException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

public class QuestionParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static List<CustomQuestion> parseQuestions(String questions) throws JsonProcessingException {
        JsonNode questionsArray = objectMapper.readTree(questions);
        try {
            return StreamSupport.stream(questionsArray.spliterator(), false)
                    .map(QuestionParser::parseEnglishQuestion)
                    .toList();
        } catch (Exception e) {
            throw new CustomQlishException("Error occurred while parsing questions json list: ", e);
        }

    }

    private static CustomQuestion parseEnglishQuestion(JsonNode questionJson) {

        try {
            var questionText = questionJson.get("question").asText();
            var subject = questionJson.get("subject").asText();
            var answer = questionJson.get("correctAnswer").asText();

            //extract options using the fields() iterator and pass them into a map
            Map<String, String> options = new HashMap<>();
            questionJson.get("options")
                    .fields().forEachRemaining(entry -> options.put(entry.getKey(), entry.getValue().asText()));


            Map<String, String> modifiers = new HashMap<>();
            modifiers.put("class", questionJson.get("class").asText());
            modifiers.put("level", questionJson.get("level").asText());
            modifiers.put("topic", questionJson.get("topic").asText());

            return CustomQuestion.builder()
                    .questionText(questionText)
                    .options(options)
                    .subject(subject)
                    .modifiers(modifiers)
                    .correctAnswer(answer)
                    .build();
        } catch (Exception e) {
            throw new CustomQlishException("Failed to parse question json object to Question entity: ", e);
        }
    }
}
