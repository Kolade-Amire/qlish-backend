package com.qlish.qlish_api.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Builder
public class TestQuestionDto {
    @EqualsAndHashCode.Include
    private ObjectId id;
    private String questionText;
    private Map<String, String> options;
}
