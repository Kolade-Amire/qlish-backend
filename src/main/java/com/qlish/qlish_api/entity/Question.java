package com.qlish.qlish_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Question {

    @Id
    private ObjectId id;
    private String questionText;
    private Map<String, String> options;
    private String answer;
}
