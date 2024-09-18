package com.qlish.qlish_api.entity;

import com.qlish.qlish_api.enums.english_enums.EnglishQuestionClass;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionLevel;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionTopic;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "english_questions")
@AllArgsConstructor
@NoArgsConstructor
public class EnglishQuestionEntity extends Question {

    @Id
    @Indexed
    private ObjectId _id;
    private EnglishQuestionClass questionClass;
    private EnglishQuestionLevel questionLevel;
    private EnglishQuestionTopic questionTopic;


}
