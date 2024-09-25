package com.qlish.qlish_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "english_questions")
@AllArgsConstructor
@NoArgsConstructor
public class EnglishQuestionEntity extends Question {

    private String questionClass;
    private String questionLevel;
    private String questionTopic;
    @Version
    private Long version;


}
