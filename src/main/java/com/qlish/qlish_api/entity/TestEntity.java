package com.qlish.qlish_api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tests")
public class TestEntity {

    @Id
    @Indexed(unique = true)
    @EqualsAndHashCode.Include
    private ObjectId _id;
    private TestDetails testDetails;
    private List<CompletedTestQuestion> questions;
    private String testStatus;
    private TestResult testResult;
}
