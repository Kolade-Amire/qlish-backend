package com.qlish.qlish_api.dto;

import com.qlish.qlish_api.entity.QuestionModifier;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Builder
public class QuestionDto {
    @EqualsAndHashCode.Include
    private ObjectId id;
    private String questionText;
    private Map<String, String> options;
    private String answer;
    private String subject;
    private Map<String, String> modifier;
}
