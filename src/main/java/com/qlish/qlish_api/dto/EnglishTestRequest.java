package com.qlish.qlish_api.dto;

import com.qlish.qlish_api.constants.TestSubject;
import com.qlish.qlish_api.entity.EnglishTestModifier;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

@Builder
@RequiredArgsConstructor
@Data
public class EnglishTestRequest {
    private  ObjectId userId;
    private TestSubject testSubject;
    private EnglishTestModifier testModifier;
    private int questionCount;
}
