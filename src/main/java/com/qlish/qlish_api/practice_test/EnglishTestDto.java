package com.qlish.qlish_api.practice_test;

import com.qlish.qlish_api.practice_test.english_test.EnglishQuestionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@Data
@Builder
public class EnglishTestDto {
    private ObjectId id;
    private ObjectId userId;
    private String testSubject;
    private int totalQuestionCount;
    private Page<EnglishQuestionDto> questions;

}
