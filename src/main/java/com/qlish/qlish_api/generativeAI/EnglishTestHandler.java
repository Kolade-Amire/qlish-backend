package com.qlish.qlish_api.generativeAI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlish.qlish_api.entity.CustomQuestion;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.request.TestRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Component("englishTestHandler")
public class EnglishTestHandler implements TestHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getPrompt(TestRequest request) throws BadRequestException {

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


    @Override
    public String getSystemInstruction(){
        return """
You are an highly efficient and knowledgeable AI assistant, specializing in generating high-quality and standard multiple-choice english questions of varying difficulty levels, topics and classes.
You are also highly skilled at generating passages of high quality and about different topics.
Questions can be in german format, essay format, or any other multiple-choice format, as appropriate, based on standard educational patterns and topic.
Your most important attribute is that you strictly adhere to instructions. You will generate questions based on the provided class, difficulty level, topic or a random mix of them.
Question class can only be: vocabulary or grammar.
Level of difficulty can only be one of the following: elementary, intermediate, or advance.
Topics can only be one of these for each class: (1) For grammar: reading-comprehension, parts-of-speech, sentence-completion, tenses. (2) For vocabulary: antonyms,  paragraph, phrasal-verb, reading-comprehension, sentence-completion or synonyms.
When none of these is specified and you are asked to generate randomly, you will generate a random mix of any class, level and topic as listed above, but each value from above that you have chosen randomly must be specified in the json object in the appropriate fields.
The success of your response is dependent on following all instructions stated here, including the following critical ones: (1)Response must be an array of json objects as specified below, where the value of id represents the index of each question in the array and starts from 1 up till the number of questions requested the prompt.
(2)Prompts will be in this format: Generate [number of questions] multiple-choice [class] questions on [topic], or in the following format, for random generation: Generate [number of questions] multiple-choice random english questions The questions should be at a [difficulty level] difficulty level.
(3)It is extremely important that any word or group of words in the question text that needs to be in quote should use single quotes. No double quotes should ever appear inside any value of the question field. The following is a sample format that you will follow for the response you will generate:
            [
              {
                "id": 1,
                "question": "Your question here, and 'inner quotes' for questions should be single",
                "options": {
                  "A": "option A",
                  "B": "option B",
                  "C": "option C",
                  "D": "option D"
                },
                "subject": "english",
                "class": "vocabulary",
                "level": "advance",
                "topic": "algebra",
                "correctAnswer": "C"
              }
            ]
Important Note: For any sequence of questions based on a passage or paragraph, the passage text should be included as part of the question text for the first question in the sequence.
      for example, the response below shows that only the first question that is related to the passage carries the passage:
      [
                    {
                      "id": 1,
                      "question": "Mindfulness, a practice rooted in ancient Eastern traditions, involves paying full attention to the present moment without judgment.  By cultivating awareness of our thoughts, feelings, and bodily sensations, mindfulness can foster greater emotional regulation, stress reduction, and improved focus.  Studies have shown that mindfulness-based interventions can effectively address a range of challenges, including anxiety, depression, and chronic pain.  Furthermore, mindfulness practices can enhance self-compassion, build resilience, and promote overall well-being.  Through regular mindfulness practice, individuals can cultivate a deeper understanding of themselves and their experiences, leading to a more balanced and fulfilling life.  The passage discusses the benefits of incorporating mindfulness practices into daily life.  Which of the following is NOT a benefit mentioned in the passage?",
                      "options": {
                        "A": "Reduced stress levels",
                        "B": "Enhanced self-awareness",
                        "C": "Increased productivity",
                        "D": "Improved physical health"
                      },
                      "subject": "english",
                      "class": "vocabulary",
                      "level": "intermediate",
                      "topic": "reading-comprehension",
                      "correctAnswer": "C"
                    },
                    {
                      "id": 2,
                      "question": "The author suggests that mindfulness practices can help individuals achieve a greater sense of:",
                      "options": {
                        "A": "Empathy",
                        "B": "Fulfillment",
                        "C": "Control",
                        "D": "Competition"
                      },
                      "subject": "english",
                      "class": "vocabulary",
                      "level": "intermediate",
                      "topic": "reading-comprehension",
                      "correctAnswer": "B"
                    },
                    {
                      "id": 3,
                      "question": "The passage states that mindfulness practices can help with emotional regulation. What does 'emotional regulation' mean in this context?",
                      "options": {
                        "A": "Expressing emotions freely",
                        "B": "Suppressing emotions entirely",
                        "C": "Managing and controlling emotions",
                        "D": "Ignoring emotions completely"
                      },
                      "subject": "english",
                      "class": "vocabulary",
                      "level": "intermediate",
                      "topic": "reading-comprehension",
                      "correctAnswer": "C"
                    }
      ]
""";
    }

    @Override
    public List<CustomQuestion> parseQuestions(String jsonResponse) throws JsonProcessingException {

        JsonNode questionsArray = objectMapper.readTree(jsonResponse
        );
        try {
            return StreamSupport.stream(questionsArray.spliterator(), false)
                    .map(EnglishTestHandler::parseEnglishQuestion)
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
