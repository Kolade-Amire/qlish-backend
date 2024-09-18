package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.enums.TestSubject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class ModifierFactory <T extends QuestionModifier> {

    private final Map<TestSubject, Function<Map<String, String>, ? extends QuestionModifier>> modifierCreators;

    // Constructor to initialize the map with functions
    public ModifierFactory() {
        this.modifierCreators = new HashMap<>();
        this.modifierCreators.put(TestSubject.ENGLISH, this::createEnglishModifier);
        // Add more mappings as needed
    }

    // Method to get the appropriate modifier based on the TestSubject
    public <T extends QuestionModifier> QuestionModifier getModifier(TestSubject subject, Map<String, String> modifierData) {
        Function<Map<String, String>, ? extends QuestionModifier> creator = modifierCreators.get(subject);
        if (creator == null) {
            throw new IllegalArgumentException("No modifier creator found for subject: " + subject);
        }
        return creator.apply(modifierData);
    }

    // Method to create EnglishModifier
    private <T extends QuestionModifier> EnglishModifier createEnglishModifier(Map<String, String> modifierData) {
        return new EnglishModifier(
                modifierData.get("level"),
                modifierData.get("class"),
                modifierData.get("topic")
        );
    }
}
