package com.qlish.qlish_api.model;

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
    private int totalQuestions;
    private int totalCorrect;
    private int totalIncorrect;
    private int scorePercentage;
    private int testPoints;
    private boolean isCompleted;
}
