package com.qlish.qlish_api.practice_test;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

@Builder
@RequiredArgsConstructor
public class TestRequest {
    private  ObjectId userId;
    private TestType testType;
    private Integer questionCount;
}
