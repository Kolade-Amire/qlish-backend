package com.qlish.qlish_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class EnglishTestDto {
    private ObjectId id;
    private ObjectId userId;
    private String testSubject;
    private int totalQuestionCount;
    private List<EnglishQuestionDto> questions;

}
