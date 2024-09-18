package com.qlish.qlish_api.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tests")
public class TestEntity {

    @Id
    @Indexed(unique = true)
    private ObjectId _id;
    private TestDetails testDetails;
    private List<TestQuestionDto> questions;
    private String testStatus;
    private TestResult testResult;
}
