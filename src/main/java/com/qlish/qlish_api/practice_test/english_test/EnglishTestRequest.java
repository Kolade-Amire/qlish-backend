package com.qlish.qlish_api.practice_test.english_test;

import com.qlish.qlish_api.practice_test.TestSubject;
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
    private Integer questionCount;
}
