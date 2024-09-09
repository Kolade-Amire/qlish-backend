package com.qlish.qlish_api.practice_test;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

@Builder
@RequiredArgsConstructor
@Data
public class TestRequest {
    private  ObjectId userId;
    private TestSubject testSubject;
    private TestModifier testModifier;
    private Integer questionCount;
}
