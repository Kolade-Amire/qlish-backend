package com.qlish.qlish_api.entity;

import com.qlish.qlish_api.enums.TestSubject;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDetails {
    private ObjectId userId;
    private TestSubject testSubject;
    private LocalDateTime startedAt;
    private int totalQuestionCount;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private int scorePercentage;
    private boolean isCompleted;
}
