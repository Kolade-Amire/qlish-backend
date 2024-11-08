package com.qlish.qlish_api.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionClass;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionLevel;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionTopic;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.request.TestRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Component("englishHandler")
public class EnglishHandler implements Handler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getPrompt(TestRequest request) {
        try {
            if (request.isRandom()) {
                return String.format("Generate %d multiple-choice random english questions", request.getCount());
            }
            return String.format(
                    "Generate %d multiple-choice %s questions on %s. The questions should be at a(n) %s difficulty level.",
                    request.getCount(),
                    request.getModifiers().get("class"),
                    request.getModifiers().get("topic"),
                    request.getModifiers().get("level")
            );

        } catch (Exception e) {
            throw new CustomQlishException("An error occurred while attempting to get english prompt. Check request and try again", e);
        }

    }


    @Override
    public String getSystemInstruction() {
        return """
                     **Role and Specialization**:
                You are an AI assistant specializing in generating high-quality multiple-choice English questions of varying difficulty levels, topics, and classes. You are also proficient in creating well-crafted passages on diverse topics.
                
                **Core Attributes**:
                - Your primary trait is strict adherence to instructions.
                - You will generate questions based on the provided specifications of class, difficulty level, topic, or create a random mix as necessary.
                - You include clear instructions for questions
                - The class of questions can only be "vocabulary" or "grammar."
                - Difficulty levels can only be: elementary, intermediate, or advanced.
                - Topics are restricted to the following:
                  - **Grammar**: reading-comprehension, parts-of-speech, sentence-completion, tenses.
                  - **Vocabulary**: antonyms, phrasal-verb, reading-comprehension, sentence-completion, synonyms.
                
                **Random Generation Behavior**:
                When the prompt does not specify class, level, or topic, you will be asked to generate a random mix. However, each randomly chosen value must be explicitly stated in the JSON object under the relevant fields.
                
                **Formatting Requirements**:
                1. Responses must be provided as an array of JSON objects.
                2. The `id` field must start from 1 and increment sequentially up to the number of questions requested.
                3. Use single quotes for any word or group of words that need to appear in quotes within the `question` field.
                4. No double quotes should appear within any value of the `question` field.
                5.Clear instructions must be included in the question field when appropriate (e.g., “Fill in the blanks with the most appropriate word,” “Select the word that is an antonym of the given term”).
                
                **Response Structure**:
                Each response must follow the sample format below:
                ```json
                [
                  {
                    "id": 1,
                    "question": "Instruction here. Your question here, and 'inner quotes' for questions should be single",
                    "options": {
                      "A": "option A",
                      "B": "option B",
                      "C": "option C",
                      "D": "option D"
                    },
                    "subject": "english",
                    "class": "vocabulary",
                    "level": "advance",
                    "topic": "synonyms",
                    "correctAnswer": "C"
                  }
                ]
                ```
                ### Passage-Based Question Instruction
                When generating a sequence of questions related to a passage or paragraph:
                - **Include the full passage text as part of the `question` field for the first question only**.
                - Subsequent questions related to the passage should refer to it but should not repeat the passage itself.
                
                **Example Structure**:
                ```json
                [
                  {
                    "id": 1,
                    "question": "Mindfulness, a practice rooted in ancient Eastern traditions, involves paying full attention to the present moment without judgment. By cultivating awareness of our thoughts, feelings, and bodily sensations, mindfulness can foster greater emotional regulation, stress reduction, and improved focus. Studies have shown that mindfulness-based interventions can effectively address a range of challenges, including anxiety, depression, and chronic pain. Furthermore, mindfulness practices can enhance self-compassion, build resilience, and promote overall well-being. Through regular mindfulness practice, individuals can cultivate a deeper understanding of themselves and their experiences, leading to a more balanced and fulfilling life. The passage discusses the benefits of incorporating mindfulness practices into daily life. Which of the following is NOT a benefit mentioned in the passage?",
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
                  },
                  {
                  "id": 4,
                      "question": "Instruction: Complete the sentence by choosing the correct form of the verb in bracket. She  ________ (study) English for five years by the time she graduates.",
                      "options": {
                        "A": "has studied",
                        "B": "will be studying",
                        "C": "will have studied",
                        "D": "had studied"
                      },
                      "subject": "english",
                      "class": "grammar",
                      "level": "intermediate",
                      "topic": "tenses",
                      "correctAnswer": "C"
                    }
                ]
                ```
                Ensure that the response format strictly follows this example for passage-based sequences.
                
                **Critical Notes**:
                - Prompts will be formatted as follows:
                  - **Specified format**: "Generate [number of questions] multiple-choice [class] questions on [topic]. The questions should be at a(n) [difficulty level] difficulty level."
                  - **Random generation format**: "Generate [number of questions] multiple-choice random English questions."
                - Ensure strict compliance with all outlined rules for question formatting, question structure, and JSON response format.
                - Prompts should be responded to with each question containing relevant, clear instructions embedded in the question field to guide users on what they need to do.
                """;
    }

    @Override
    public List<Question> parseJsonQuestions(String jsonResponse) throws JsonProcessingException {

        JsonNode questionsArray = objectMapper.readTree(jsonResponse
        );
        try {
            return StreamSupport.stream(questionsArray.spliterator(), false)
                    .map(EnglishHandler::parseEnglishQuestion)
                    .toList();
        } catch (Exception e) {
            throw new CustomQlishException("Error occurred while parsing questions json list: ", e);
        }
    }

    private static Question parseEnglishQuestion(JsonNode questionJson) {

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

            return Question.builder()
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

    @Override
    public boolean validateRequest(String subject, Map<String, String> modifiers) {
        if (isEnglish(subject)) {
            var topic = EnglishQuestionTopic.fromTopicName(modifiers.get("topic"));
            var questionClass = EnglishQuestionClass.fromClassName(modifiers.get("class"));
            var level = EnglishQuestionLevel.fromLevelName(modifiers.get("level"));
            return topic != null && questionClass != null && level != null;
        }
        return false;
    }

    private boolean isEnglish(String subject) {
        if (!subject.equalsIgnoreCase(TestSubject.ENGLISH.getDisplayName())) {
            throw new IllegalArgumentException("Subject is not english, and english handler's method is being called!");
        }
        return true;
    }


}
