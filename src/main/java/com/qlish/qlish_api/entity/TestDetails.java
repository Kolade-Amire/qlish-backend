package com.qlish.qlish_api.entity;

import com.qlish.qlish_api.constants.TestSubject;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;


@Data
@RequiredArgsConstructor
@Builder
public class TestDetails {
    private ObjectId userId;
    private TestSubject testSubject;
    private LocalDateTime startedAt;
    private int totalQuestionCount;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private boolean isCompleted;
}
