package com.qlish.qlish_api.dto;

import com.qlish.qlish_api.constants.TestSubject;
import com.qlish.qlish_api.entity.TestDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class EnglishTestDto {
    private ObjectId id;
    private ObjectId userId;
    private String testSubject;
    private LocalDateTime startedAt;
    private String testStatus;
    private int totalQuestionCount;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
}
