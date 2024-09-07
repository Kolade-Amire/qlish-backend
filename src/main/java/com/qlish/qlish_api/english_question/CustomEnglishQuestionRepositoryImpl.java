package com.qlish.qlish_api.english_question;

import com.qlish.qlish_api.exception.QuestionsRetrievalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<EnglishQuestionEntity> findQuestionsByCriteria(Pageable pageable, @Nullable Level questionLevel, @Nullable QuestionClass questionClass, @Nullable Topic questionTopic, int testSize) {

        try {
            Criteria criteria = new Criteria();

            //only applies criteria for non-null fields
            if (questionLevel != null) {
                criteria.and("questionLevel").regex(questionLevel, "i");
            }
            if (questionClass != null) {
                criteria.and("questionClass").regex(questionClass, "i");
            }
            if (questionTopic != null) {
                criteria.and("questionTopic").regex(questionTopic, "i");
            }

            //sample of test size
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.sample(testSize)
            );

            List<EnglishQuestionEntity> resultList = mongoTemplate.aggregate(aggregation, "english_questions", EnglishQuestionEntity.class).getMappedResults();

            //Apply pagination
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), resultList.size());

            var pagedResult = resultList.subList(start, end);

            return new PageImpl<>(pagedResult, pageable, resultList.size());
        } catch (Exception e) {
            throw new QuestionsRetrievalException("Failed to retrieve questions.");
        }


    }
}
