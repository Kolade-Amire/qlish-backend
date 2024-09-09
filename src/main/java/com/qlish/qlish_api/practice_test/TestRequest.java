package com.qlish.qlish_api.practice_test;

import com.qlish.qlish_api.question.Question;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Set;

@Builder
@RequiredArgsConstructor
@Data
public class TestRequest {

    private ObjectId userId;
    private TestEntity testEntity;
    private TestSubject testSubject;
    private Set<Question> questionSet;
}
