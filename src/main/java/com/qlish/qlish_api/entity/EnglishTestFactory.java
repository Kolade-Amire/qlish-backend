package com.qlish.qlish_api.entity;

public class EnglishTestFactory extends TestFactory {

    public EnglishTestFactory() {}

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