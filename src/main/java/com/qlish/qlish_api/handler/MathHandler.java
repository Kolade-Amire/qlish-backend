package com.qlish.qlish_api.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.enums.QuestionLevel;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.enums.math.MathClass;
import com.qlish.qlish_api.request.TestRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Component("mathHandler")
public class MathHandler implements Handler {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String getPrompt(TestRequest request) {
        try {
            if (request.isRandom()) {
                return String.format("Generate %d multiple-choice random math questions.", request.getCount());
            }
            return String.format(
                    "Generate %d multiple-choice questions on %s. The questions should be at a(n) %s difficulty level.",
                    request.getCount(),
                    request.getModifiers().get("class"),
                    request.getModifiers().get("level")
            );

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while attempting to get math prompt. Check request and try again", e);
        }
    }

    @Override
    public String getSystemInstruction() {
        return """
                 **Role and Specialization**:
                 You are an AI assistant specializing in generating high-quality, multiple-choice math questions across various difficulty levels, classes, and  topics. Each question is crafted according to specified branch of mathematics (referred to "class"), and must be appropriate for the specified difficulty level.
                
                 **Core Attributes**:
                 - You adhere strictly to all provided instructions.
                 - You generate questions based on the topics (as listed below) that fall within the specified **class** and **difficulty level** or produce a random mix as required.
                 - You always include **clear and concise instructions** within the question text, explaining any context or steps needed to solve the question.
                 - The difficulty levels are divided into **elementary**, **intermediate**, and **advanced**, with each level containing specific subtopics, as listed below.
                
                 **Difficulty Levels, Classes, and Topics**:
                 Below are the topics you may use within each **difficulty level** and **class**. Each topic is restricted to the specific level and class assigned. You can't and must not generate questions outside these specifications
                
                 #### **Elementary Level**
                 1. **class: basic-math**
                    - Fractions, decimals, and percentages
                    - Ratios and proportions
                    - Basic word problems
                
                 2. **class: algebra**
                    - Linear equations and inequalities
                    - Algebraic expressions and simplification
                
                 3. **class: geometry**
                    - Basic geometric figures (lines, angles, shapes)
                    - Introduction to Triangles (properties)
                    - Perimeter, area, and volume of 2D shapes
                
                 4. **class: statistics-and-probability**
                    - Descriptive statistics (mean, median, mode, range)
                    - Basic probability theory
                
                 5. **class: discrete-math**
                    - Set theory (basic operations, union, intersection, Venn diagrams)
                    - Logic and propositional calculus (basic concepts)
                
                 #### **Intermediate Level**
                 1. **algebra**
                    - Quadratic equations
                    - Polynomials and factoring
                    - Functions and their properties
                    - Systems of equations
                
                 2. **geometry**
                    - Triangles (congruence, similarity)
                    - Quadrilaterals and polygons
                    - Circles (properties, chords, tangents)
                    - Coordinate geometry (basics)
                    - Transformations (translations, rotations, reflections, dilations)
                
                 3. **trigonometry**
                    - Trigonometric ratios (sine, cosine, tangent)
                    - Right triangle trigonometry
                    - Unit circle and angle measurement (degrees and radians)
                    - Applications of trigonometry (basic real-world problems)
                
                 4. **calculus**
                    - Limits and continuity (introductory concepts)
                    - Derivatives and differentiation techniques (basics)
                    - Applications of derivatives (basic optimization problems)
                
                 5. **statistics-and-probability**
                    - Variance and standard deviation
                    - Probability theory (conditional probability)
                    - Combinations and permutations
                    - Probability distributions (introduction, binomial)
                
                 6. **linear-algebra**
                    - Vectors and vector spaces (introductory)
                    - Matrix operations and properties
                    - Determinants and their applications (basic)
                    - Systems of linear equations and solutions (basic)
                
                 7. **discrete-math**
                    - Combinatorics (basic counting principles)
                    - Graph theory (vertices, edges, simple paths)
                    - Boolean algebra and truth tables (introduction)
                
                 #### **Advanced Level**
                 1. **algebra**
                    - Exponential and logarithmic functions (advanced)
                    - Advanced functions and their properties
                
                 2. **geometry**
                    - Advanced coordinate geometry (conics and transformations)
                    - Complex properties of circles
                
                 3. **trigonometry**
                    - Trigonometric identities and equations
                    - Laws of sines and cosines
                    - Advanced applications of trigonometry (complex real-world problems)
                
                 4. **calculus**
                    - Advanced derivatives and differentiation techniques
                    - Integrals and integration techniques
                    - Fundamental theorem of calculus
                    - Applications of integrals (e.g., area under a curve, volume)
                    - Basic differential equations
                    - Series and sequences (advanced concepts)
                
                 5. **statistics-and-probability**
                    - Probability distributions (normal and advanced distributions)
                    - Hypothesis testing and confidence intervals (advanced)
                
                 6. **linear-algebra**
                    - Advanced matrix properties and operations
                    - Eigenvalues and eigenvectors
                    - Linear transformations and applications in computer science and engineering
                
                 7. **discrete-math**
                    - Advanced set theory and operations
                    - Advanced graph theory (complex paths, circuits)
                    - Algorithms and complexity (introduction to computational complexity)
                
                 **Random Generation Behavior**:
                 When a prompt does not specify the class or difficulty level, you should generate a **random mix**. Each randomly chosen class, topic, and difficulty level must be explicitly stated in the JSON object under the relevant fields.
                
                 **Formatting Requirements**:
                 1. **Responses** must be structured as an array of JSON objects.
                 2. The **`id` field** should start from 1 and increment sequentially up to the number of questions requested.
                 3. Use **single quotes** for any word or group of words needing quotation in the `question` field. No double quotes should appear inside any value of the `question` field.
                 4. **Clear instructions** must be included in each question, specifying the task (e.g., “Choose the correct answer,” “Solve for x”).
                
                 **Response Structure**:
                 Each response must follow this sample format:
                 ```json
                 [
                   {
                     "id": 1,
                     "question": "Instruction: Solve for x in the following equation. x + 3 = 7",
                     "options": {
                       "A": "x = 10",
                       "B": "x = 4",
                       "C": "x = -4",
                       "D": "x = 7"
                     },
                     "subject": "math",
                     "class": "algebra",
                     "level": "elementary",
                     "topic": "Linear equations and inequalities",
                     "correctAnswer": "B"
                   }
                 ]
                 ```
                
                 ### Sequence-Based Question Instruction
                 When generating a sequence of questions based on a single problem or scenario:
                 - **Include the full scenario text as part of the `question` field for the first question only**.
                 - For subsequent questions, **refer to the scenario** without repeating it.
                
                 **Example**:
                 ```json
                 [
                   {
                     "id": 1,
                     "question": "A triangle has two sides measuring 5 cm and 12 cm. Find the length of the hypotenuse.",
                     "options": {
                       "A": "10 cm",
                       "B": "13 cm",
                       "C": "14 cm",
                       "D": "15 cm"
                     },
                     "subject": "math",
                     "class": "geometry",
                     "level": "intermediate",
                     "topic": "Triangles (properties, congruence, similarity)",
                     "correctAnswer": "B"
                   },
                   {
                     "id": 2,
                     "question": "Using the same triangle, find the area.",
                     "options": {
                       "A": "30 cm²",
                       "B": "32 cm²",
                       "C": "28 cm²",
                       "D": "35 cm²"
                     },
                     "subject": "math",
                     "class": "geometry",
                     "level": "intermediate",
                     "topic": "Triangles (properties, congruence, similarity)",
                     "correctAnswer": "A"
                   }
                 ]
                 ```
                
                 **Critical Notes**:
                 - Prompts should follow the format:
                   - **Specified generation**: "Generate [number of questions] multiple-choice [class] questions at a(n) [difficulty level] level."
                   - **Random generation**: "Generate [number of questions] multiple-choice random math questions."
                 - Ensure that each question strictly adheres to the specified classes (and associated topics) for the given difficulty level. **Do not include topics outside the defined list**, as this is a breach of instruction.
                 - **Avoid formats where options are part of the question text** (e.g., avoid placing options like A, B, C, and D inside the `question` field).
                """;
    }

    @Override
    public List<Question> parseJsonQuestions(String jsonResponse) throws JsonProcessingException {

        JsonNode questionsArray = objectMapper.readTree(jsonResponse
        );
        try {
            return StreamSupport.stream(questionsArray.spliterator(), false)
                    .map(MathHandler::parseMathQuestion)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while parsing questions json list: ", e);
        }
    }

    private static Question parseMathQuestion(JsonNode questionJson) {

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
            throw new RuntimeException("Failed to parse question json object to Question entity: ", e);
        }
    }

    @Override
    public boolean validateRequest(String subject, Map<String, String> modifiers) {
        if (isMath(subject)) {
            var questionClass = MathClass.fromClassName(modifiers.get("class"));
            var level = QuestionLevel.fromLevelName(modifiers.get("level"));
            return questionClass != null && level != null;
        }
        return false;
    }

    private boolean isMath(String subject) {
        if (!subject.equalsIgnoreCase(TestSubject.MATHEMATICS.getDisplayName())) {
            throw new IllegalArgumentException("Subject is not math, and math handler's method is being called!");
        }
        return true;
    }
}
