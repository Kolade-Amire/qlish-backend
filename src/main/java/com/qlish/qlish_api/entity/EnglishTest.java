package com.qlish.qlish_api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class EnglishTest {

    @Id
    @Indexed
    private ObjectId _id;
    private TestDetails testDetails;
    private Map<String, String > modifier;
    private List<TestQuestionDto> questions;
    private String testStatus;
}
