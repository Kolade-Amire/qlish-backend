package com.qlish.qlish_api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Map;

@Builder
@RequiredArgsConstructor
@Data
public class EnglishTestRequest {
    private  ObjectId userId;
    private Map<String, String> modifier;
    private int questionCount;
}
