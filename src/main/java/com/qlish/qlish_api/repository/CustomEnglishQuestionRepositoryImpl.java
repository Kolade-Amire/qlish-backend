package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.exception.QuestionsRetrievalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class CustomEnglishQuestionRepositoryImpl implements CustomEnglishQuestionRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<EnglishQuestionEntity> findQuestionsByCriteria(Map<String, String> modifiers, int testSize) {

        try {
            Criteria criteria = new Criteria();

            //only applies criteria for non-null modifier fields
            if (modifiers.get("level") != null) {
                criteria.and("questionLevel").regex(modifiers.get("level"), "i");
            }
            if (modifiers.get("class") != null) {
                criteria.and("questionClass").regex(modifiers.get("class"), "i");
            }
            if (modifiers.get("topic") != null) {
                criteria.and("questionTopic").regex(modifiers.get("class"), "i");
            }

            //sample of test size
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.sample(testSize)
            );

            return mongoTemplate.aggregate(aggregation, "english_questions", EnglishQuestionEntity.class).getMappedResults();


        } catch (Exception e) {
            throw new QuestionsRetrievalException("Failed to retrieve questions.");
        }


    }
}
