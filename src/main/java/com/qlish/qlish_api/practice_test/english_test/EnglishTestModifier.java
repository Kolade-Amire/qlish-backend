package com.qlish.qlish_api.practice_test.english_test;

import com.qlish.qlish_api.practice_test.TestModifier;

public class EnglishTestModifier extends TestModifier {

    public void setQuestionClass(String className) {
        addModifier("questionClass", className);
    }

    public void setQuestionLevel(String levelName) {
        addModifier("questionLevel", levelName);
    }

    public void setQuestionTopic(String topicName) {
        addModifier("questionTopic", topicName);
    }


}