package com.qlish.qlish_api.model;

import com.qlish.qlish_api.enums.DifficultyLevel;
import com.qlish.qlish_api.enums.TestStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@Document(collection = "tests")
public class TestEntity {
    @Id
    @Indexed(unique = true)
    @EqualsAndHashCode.Include
    private ObjectId id;
    private TestDetails testDetails;
    private List<TestQuestion> questions;
    private TestStatus testStatus;
    private boolean isQuiz; //validate across
    private DifficultyLevel difficultyLevel; //validate across
}
