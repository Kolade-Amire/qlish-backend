package com.qlish.qlish_api.dto;

import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.TestDetails;
import com.qlish.qlish_api.constants.TestSubject;
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
    private TestDetails testDetails;
    private TestSubject testSubject;
    private Set<Question> questionSet;
}
