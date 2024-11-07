package com.qlish.qlish_api.dto;

import com.qlish.qlish_api.entity.TestQuestion;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Builder
public class TestDto {
    @EqualsAndHashCode.Include
    private String id;
    private ObjectId userId;
    private String testSubject;
    private List<TestQuestion> questions;
    private LocalDateTime startedAt;
    private String testStatus;
    private int totalQuestionCount;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private int scorePercentage;
}
