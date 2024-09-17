package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.enums.english_enums.EnglishQuestionClass;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionLevel;
import com.qlish.qlish_api.enums.english_enums.EnglishQuestionTopic;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.exception.QuestionsRetrievalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomEnglishQuestionRepositoryImpl implements CustomEnglishQuestionRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<EnglishQuestionEntity> findQuestionsByCriteria(@Nullable EnglishQuestionLevel questionEnglishQuestionLevel, @Nullable EnglishQuestionClass englishQuestionClass, @Nullable EnglishQuestionTopic questionEnglishQuestionTopic, int testSize) {

        try {
            Criteria criteria = new Criteria();

            //only applies criteria for non-null modifier fields
            if (questionEnglishQuestionLevel != null) {
                criteria.and("questionLevel").regex(questionEnglishQuestionLevel.getLevelName(), "i");
            }
            if (englishQuestionClass != null) {
                criteria.and("questionClass").regex(englishQuestionClass.getClassName(), "i");
            }
            if (questionEnglishQuestionTopic != null) {
                criteria.and("questionTopic").regex(questionEnglishQuestionTopic.getTopicName(), "i");
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
