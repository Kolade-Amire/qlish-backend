package com.qlish.qlish_api.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;


@Data
@RequiredArgsConstructor
public abstract class TestEntity {
    public ObjectId _id;
    private ObjectId userId;
    private String testSubject;
    private LocalDateTime startedAt;
    private int totalQuestionCount;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private boolean isCompleted;
}
