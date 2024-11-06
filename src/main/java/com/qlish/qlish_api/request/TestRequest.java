package com.qlish.qlish_api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;


@AllArgsConstructor
@Getter
public class TestRequest {
    private String userId;
    private String subject;
    private Map<String, String> modifiers;
    private int count;
    private boolean isRandom;
}
