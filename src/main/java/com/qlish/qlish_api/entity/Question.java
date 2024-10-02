package com.qlish.qlish_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Question {

    @Id
    private ObjectId id;
    @Field("question")
    private String questionText;
    private Map<String, String> options;
    private String answer;
}
