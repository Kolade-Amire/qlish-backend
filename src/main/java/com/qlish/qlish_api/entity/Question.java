package com.qlish.qlish_api.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@Document(collection = "questions")
public class Question{
    @EqualsAndHashCode.Include
    @Id
    private ObjectId id;
    private String questionText;
    private Map<String, String> options;
    private String subject;
    private Map<String, String> modifiers;
    private String correctAnswer;
}
