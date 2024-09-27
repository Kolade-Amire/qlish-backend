package com.qlish.qlish_api.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "english_questions")
public class EnglishQuestionEntity extends Question {

    private String questionClass;
    private String questionLevel;
    private String questionTopic;
    @Version
    private Long version;

    public EnglishQuestionEntity(ObjectId id, String questionText, Map<String, String> options, String answer, String questionClass, String questionLevel, String questionTopic) {
        super(id, questionText, options, answer);
        this.questionClass = questionClass;
        this.questionLevel = questionLevel;
        this.questionTopic = questionTopic;
    }


}
