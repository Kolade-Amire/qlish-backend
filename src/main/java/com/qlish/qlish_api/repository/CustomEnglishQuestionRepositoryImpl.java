package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.exception.QuestionsRetrievalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomEnglishQuestionRepositoryImpl implements QuestionRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<EnglishQuestionEntity> getTestQuestions(QuestionModifier mod, int size) {

        try {
            EnglishModifier modifier = (EnglishModifier) mod;
            Criteria criteria = new Criteria();

            //only applies criteria for non-null modifier fields
            if (modifier.getLevel() != null) {
                criteria.and("questionLevel").regex(modifier.getLevel(), "i");
            }
            if (modifier.getQuestionClass() != null) {
                criteria.and("questionClass").regex(modifier.getQuestionClass(), "i");
            }
            if (modifier.getTopic() != null) {
                criteria.and("questionTopic").regex(modifier.getTopic(), "i");
            }

            //sample of test size
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.sample(size)
            );

            return mongoTemplate.aggregate(aggregation, "english_questions", EnglishQuestionEntity.class).getMappedResults();


        } catch (Exception e) {
            throw new QuestionsRetrievalException("Failed to retrieve questions.");
        }


    }

}
