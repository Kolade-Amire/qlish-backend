package com.qlish.qlish_api.model;

import com.qlish.qlish_api.enums.DifficultyLevel;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.enums.TestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class TestDetails {
    private ObjectId userId;
    private TestType testType;
    private TestSubject testSubject;
    private DifficultyLevel difficultyLevel;
    private LocalDateTime startedAt;
    private int totalQuestions;
    private int totalCorrect;
    private int totalIncorrect;
    private int scorePercentage;
    private int pointsEarned;
    private boolean isCompleted;
}
