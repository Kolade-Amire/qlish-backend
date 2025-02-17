package com.qlish.qlish_api.model;

import com.qlish.qlish_api.enums.TestSubject;
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
    private TestSubject subject;
    private Map<String, String> modifiers;
    private String correctAnswer;
}
