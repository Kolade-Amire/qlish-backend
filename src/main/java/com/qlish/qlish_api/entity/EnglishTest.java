package com.qlish.qlish_api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@Document(collection = "english_tests")
public class EnglishTest {

    @Id
    @Indexed
    private ObjectId _id;
    private TestDetails testDetails;
    private EnglishTestModifier testModifier;
    private List<EnglishQuestionEntity> questions;
}
