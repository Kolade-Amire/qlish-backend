package com.qlish.qlish_api.entity;

import com.qlish.qlish_api.dto.QuestionFeedback;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@Document(collection = "tests")
public class TestEntity {

    @Id
    @Indexed(unique = true)
    private ObjectId _id;
    private ObjectId userId;
    private String testSubject;
    private TestModifier testModifier;
    private List<QuestionFeedback> questions;
    private LocalDateTime startedAt;
    private int totalQuestionCount;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private boolean isCompleted;
}
