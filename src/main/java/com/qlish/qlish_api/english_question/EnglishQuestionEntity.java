package com.qlish.qlish_api.english_question;

import com.qlish.qlish_api.question.Question;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "english_questions")
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class EnglishQuestionEntity extends Question {
    @Id
    @Indexed
    private ObjectId _id;
    private QuestionClass questionClass;
    private Level level;
    private Topic questionTopic;

}