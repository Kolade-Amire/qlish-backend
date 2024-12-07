package com.qlish.qlish_api.model;

import com.qlish.qlish_api.enums.TestStatus;
import lombok.*;
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
    @EqualsAndHashCode.Include
    private ObjectId id;
    private TestDetails testDetails;
    private TestStatus testStatus;
    private List<TestQuestion> questions;
}
