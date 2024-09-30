package com.qlish.qlish_api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.Map;


@AllArgsConstructor
@Getter
public class TestRequest {
    private ObjectId userId;
    private Map<String, String> modifiers;
    private String testSubject;
    private int questionCount;
}
