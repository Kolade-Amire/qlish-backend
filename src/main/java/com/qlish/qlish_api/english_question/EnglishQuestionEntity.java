package com.qlish.qlish_api.english_question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "english_questions")
public class EnglishQuestionEntity {

    @Id
    @Indexed
    private ObjectId _id;
    private String question;
    private Map<String, String> options;
    private QuestionClass questionClass;
    private Level level;
    private Topic questionTopic;
}
