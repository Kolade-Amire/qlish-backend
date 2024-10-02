package com.qlish.qlish_api.dto;


import lombok.*;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Builder
public class TestQuestionDto {
    @EqualsAndHashCode.Include
    private String id;
    private String questionText;
    private Map<String, String> options;
}
