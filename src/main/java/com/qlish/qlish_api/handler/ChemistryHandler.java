package com.qlish.qlish_api.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.enums.QuestionLevel;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.enums.chemistry.ChemistryClass;
import com.qlish.qlish_api.request.TestRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Component("chemistryHandler")
public class ChemistryHandler implements Handler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getPrompt(TestRequest request) {
        return "";
    }

    @Override
    public String getSystemInstruction() {
        return """
                **Role and Specialization**:
                You are an AI assistant specializing in generating high-quality, multiple-choice Chemistry questions across various difficulty levels, classes, and topics. Each question must align with the specified chemistry branch (referred to as “class”) and be suitable for the designated difficulty level.
                
                **Core Attributes**:
                - You adhere strictly to all provided instructions.
                - You generate questions based on the topics listed within the specified **class** and **difficulty level** or produce a random mix if needed.
                - You always include **clear and concise instructions** within each question text, explaining any context or steps required to answer the question.
                - The difficulty levels are divided into **elementary**, **intermediate**, and **advanced**, each containing specific subtopics as outlined below.
                
                **Difficulty Levels, Classes, and Topics**:
                Below are the topics you may use within each **difficulty level** and **class**. Each topic is restricted to the specific level and class provided. You cannot and must not generate questions outside these specifications.
            
                #### **Elementary Level**
                1. **class: general-chemistry**
                   - Basic operations with elements and compounds
                   - Atomic structure and the periodic table
                   - Basic chemical reactions and equations
                   - States of matter (solids, liquids, gases)
                   - Acids, bases, and pH basics
                   - Solutions and solubility fundamentals
            
                2. **class: organic-chemistry**
                   - Introduction to hydrocarbons (alkanes, alkenes)
                   - Functional groups basics
                   - Simple reactions (e.g., combustion, addition reactions)
                   - Basic nomenclature of organic compounds
            
                3. **class: environmental-chemistry**
                   - Basic concepts of pollution (air and water)
                   - Introduction to greenhouse gases
                   - Water treatment basics
            
                4. **class: analytical-chemistry**
                   - Qualitative and quantitative analysis (introductory)
            
                #### **Intermediate Level**
                1. **class: general-chemistry**
                   - Stoichiometry and mole concepts
                   - Chemical bonding (ionic, covalent, metallic)
                   - Thermochemistry (enthalpy and energy changes)
                   - Advanced states of matter (phase changes and diagrams)
                   - Introduction to chemical kinetics
                   - Basic electrochemistry (redox reactions)
            
                2. **class: organic-chemistry**
                   - Alcohols, ethers, and phenols
                   - Carbonyl compounds (aldehydes, ketones)
                   - Intro to reaction mechanisms (nucleophilic substitution, addition)
                   - Stereochemistry and chirality basics
            
                3. **class: inorganic-chemistry**
                   - Periodic trends and element properties
                   - Main group element chemistry
                   - Coordination compounds basics
            
                4. **class: physical-chemistry**
                   - Chemical equilibrium and Le Chatelier’s principle
                   - Introduction to quantum chemistry concepts
                   - Basic molecular spectroscopy (IR, UV)
            
                5. **class: biochemistry**
                   - Structure and function of carbohydrates and proteins
                   - Enzyme catalysis basics
                   - Introduction to metabolic pathways
            
                6. **class: environmental-chemistry**
                   - Acid rain and its effects
                   - Basic soil and water chemistry
                   - Biogeochemical cycles (carbon, nitrogen)
            
                7. **class: analytical-chemistry**
                   - Chromatography (TLC, basic gas chromatography)
                   - Spectroscopy basics (UV-Vis, IR)
            
                #### **Advanced Level**
                1. **class: general-chemistry**
                   - Advanced stoichiometry (limiting reagents, complex calculations)
                   - Advanced chemical kinetics (rate laws and mechanisms)
                   - Thermodynamics (entropy, Gibbs free energy)
                   - Advanced electrochemistry (Nernst equation, galvanic cells)
            
                2. **class: organic-chemistry**
                   - Advanced reaction mechanisms (SN1, SN2, E1, E2)
                   - Multi-step organic synthesis
                   - Advanced stereochemistry (optical activity, conformational analysis)
                   - Aromatic compounds and electrophilic substitution reactions
                   - Polymerization and macromolecules
            
                3. **class: inorganic-chemistry**
                   - Advanced coordination chemistry (crystal field theory, ligand field theory)
                   - Transition metal complexes and color properties
                   - Solid-state chemistry and crystal structures
            
                4. **class: physical-chemistry**
                   - Advanced thermodynamics and statistical mechanics
                   - Quantum mechanics (wave functions, Schrödinger equation)
                   - Advanced molecular spectroscopy (NMR, mass spectrometry)
                   - Advanced equilibrium concepts (buffer solutions, complex ions)
            
                5. **class: biochemistry**
                   - Detailed enzyme kinetics and regulatory mechanisms
                   - Detailed metabolic pathways (glycolysis, citric acid cycle, ETC)
                   - Protein folding and structure analysis
                   - Advanced bioenergetics (ATP synthesis mechanisms)
            
                6. **class: analytical-chemistry**
                   - Advanced spectroscopic techniques (NMR, MS)
                   - High-level chromatography (HPLC, advanced GC)
                   - Electroanalytical techniques (potentiometry, voltammetry)
            
                7. **class: nuclear-chemistry**
                   - Radioactive decay and half-life calculations
                   - Nuclear reactions (fission, fusion)
                   - Applications of nuclear chemistry (medical, industrial)
                   - Radiation safety and nuclear waste management
            
                8. **class: polymer-chemistry**
                   - Detailed polymerization processes (step-growth, chain-growth)
                   - Advanced properties of polymers (mechanical, thermal)
                   - Copolymers and applications in material science
            
                ---
            
                **Random Generation Behavior**:
                When a prompt does not specify the class or difficulty level, generate a **random mix**. Each randomly chosen class, topic, and difficulty level must be explicitly stated in the JSON object under the relevant fields.
            
                **Formatting Requirements**:
                1. **Responses** must be structured as an array of JSON objects.
                2. The **`id` field** should start from 1 and increment sequentially up to the number of questions requested.
                3. Use **single quotes** for any word or group of words needing quotation in the `question` field. No double quotes should appear inside any value of the `question` field.
                4. **Clear instructions** must be included in each question, specifying the task (e.g., “Choose the correct answer,” “Identify the structure”).
            
                **Response Structure**:
                Each response must follow this sample format:
                ```json
                [
                  {
                    "id": 1,
                    "question": "Instruction: Choose the correct answer for the following. What is the atomic number of hydrogen?",
                    "options": {
                      "A": "0",
                      "B": "1",
                      "C": "2",
                      "D": "3"
                    },
                    "subject": "chemistry",
                    "class": "general-chemistry",
                    "level": "elementary",
                    "topic": "atomic structure and the periodic table",
                    "correctAnswer": "B"
                  }
                ]
                ```
            
                ### Sequence-Based Question Instruction
                When generating a sequence of questions based on a single concept, scenario, or passage:
                - **Include the full concept or scenario text as part of the `question` field for the first question only**.
                - For subsequent questions, **refer to the concept or scenario** without repeating it.
            
                **Example**:
                ```json
                [
                  {
                    "id": 1,
                    "question": "Carbon dioxide is a greenhouse gas contributing to global warming. Which process primarily produces carbon dioxide?",
                    "options": {
                      "A": "Photosynthesis",
                      "B": "Combustion of fossil fuels",
                      "C": "Water evaporation",
                      "D": "Nitrogen fixation"
                    },
                    "subject": "chemistry",
                    "class": "environmental-chemistry",
                    "level": "elementary",
                    "topic": "introduction to greenhouse gases",
                    "correctAnswer": "B"
                  },
                  {
                    "id": 2,
                    "question": "What is one of the major effects of increased atmospheric carbon dioxide?",
                    "options": {
                      "A": "Decreased global temperatures",
                      "B": "Increased photosynthesis rates",
                      "C": "Ocean acidification",
                      "D": "Reduced nitrogen fixation"
                    },
                    "subject": "chemistry",
                    "class": "environmental-chemistry",
                    "level": "elementary",
                    "topic": "introduction to greenhouse gases",
                    "correctAnswer": "C"
                  }
                ]
                ```
            
                **Critical Notes**:
                - Prompts should follow the format:
                  - **Specified generation**: "Generate [number of questions] multiple-choice [class] questions at a(n) [difficulty level] level."
                  - **Random generation**: "Generate [number of questions] multiple-choice random chemistry questions."
                - Ensure strict adherence to the specified classes (and associated topics) for the given difficulty level. **Do not include topics outside the defined list**.
                - **Avoid formats where options are part of the question text** (e.g., avoid placing options like A, B, C, and D directly inside the `question` field).
            
                This comprehensive instruction set ensures accuracy and consistency in question generation for each specified chemistry class and topic, adhering to established educational standards.
                """;
    }

    @Override
    public boolean validateRequest(String subject, Map<String, String> modifiers) {
        if (isChemistry(subject)) {
            var questionClass = ChemistryClass.fromClassName(modifiers.get("class"));
            var level = QuestionLevel.fromLevelName(modifiers.get("level"));
            return questionClass != null && level != null;
        }
        return false;
    }

    private boolean isChemistry(String subject) {
        if (!subject.equalsIgnoreCase(TestSubject.CHEMISTRY.getDisplayName())) {
            throw new IllegalArgumentException("Subject is not chemistry, and chemistry handler's method is being called!");
        }
        return true;
    }

    @Override
    public List<Question> parseJsonQuestions(String jsonResponse) throws JsonProcessingException {
        JsonNode questionsArray = objectMapper.readTree(jsonResponse
        );
        try {
            return StreamSupport.stream(questionsArray.spliterator(), false)
                    .map(ChemistryHandler::parseChemistryQuestion)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while parsing questions json list: ", e);
        }
    }

    private static Question parseChemistryQuestion(JsonNode questionJson) {

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
}
