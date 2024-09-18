package com.qlish.qlish_api.entity;

import com.qlish.qlish_api.enums.english_enums.EnglishQuestionClass;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionLevel;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionTopic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "english_questions")
@AllArgsConstructor
@NoArgsConstructor
public class EnglishQuestionEntity extends Question {

    private EnglishQuestionClass questionClass;
    private EnglishQuestionLevel questionLevel;
    private EnglishQuestionTopic questionTopic;


}
