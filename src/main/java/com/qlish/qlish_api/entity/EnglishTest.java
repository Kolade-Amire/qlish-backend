package com.qlish.qlish_api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@Document(collection = "english_tests")
public class EnglishTest extends TestEntity {
    private EnglishTestModifier testModifier;
    private List<EnglishQuestionEntity> questions;
}
