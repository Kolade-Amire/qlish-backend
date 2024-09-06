package com.qlish.qlish_api.practice_test;

import com.qlish.qlish_api.question.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@Document(collection = "tests")
public class TestEntity {

    @Id
    @Indexed(unique = true)
    private ObjectId _id;
    private ObjectId userId;
    private TestType testType;
    private Set<QuestionEntity> questionSet;
    private LocalDateTime startedAt;
    private Integer questionCount;
    private Integer correctAnswerCount;

}
