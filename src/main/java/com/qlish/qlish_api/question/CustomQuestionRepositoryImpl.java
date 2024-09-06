package com.qlish.qlish_api.question;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<QuestionEntity> findQuestionsByCriteria(String questionLevel, String questionClass, String questionTopic) {
        Query query = new Query();

        if (questionLevel != null) {
            query.addCriteria(Criteria.where("questionLevel").regex(questionLevel, "i"));
        }
        if (questionClass != null) {
            query.addCriteria(Criteria.where("questionClass").regex(questionClass, "i"));
        }
        if (questionTopic != null) {
            query.addCriteria(Criteria.where("questionTopic").regex(questionTopic, "i"));
        }

        return mongoTemplate.find(query, QuestionEntity.class);

    }
}
