package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.exception.QuestionsRetrievalException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Qualifier("english")
public class CustomEnglishQuestionRepository implements QuestionRepository<EnglishQuestionEntity> {

    private final MongoTemplate mongoTemplate;
    private final EnglishRepository repository;

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
            throw new QuestionsRetrievalException(AppConstants.TEST_QUESTIONS_RETRIEVAL_ERROR);
        }


    }

    @Override
    public Page<EnglishQuestionEntity> getAllQuestionsByCriteria(QuestionModifier mod, Pageable pageable) {
        try {

            var questionModifier = (EnglishModifier) mod;

            var criteria = new Criteria();
            if (questionModifier.getLevel() != null) {
                criteria.and("questionLevel").regex(questionModifier.getLevel(), "i");
            }
            if (questionModifier.getQuestionClass() != null) {
                criteria.and("questionClass").regex(questionModifier.getQuestionClass(), "i");
            }
            if (questionModifier.getTopic() != null) {
                criteria.and("questionTopic").regex(questionModifier.getTopic(), "i");
            }

            // Build query with the criteria
            Query query = new Query(criteria).with(pageable);

            // Fetch total count of matching documents
            long total = mongoTemplate.count(query, EnglishQuestionEntity.class, "english_questions");

            // Fetch paginated results
            List<EnglishQuestionEntity> results = mongoTemplate.find(query, EnglishQuestionEntity.class, "english_questions");

            // Return a Page object containing the results
            return new PageImpl<>(results, pageable, total);

        } catch (Exception e) {
            throw new QuestionsRetrievalException(AppConstants.TEST_QUESTIONS_RETRIEVAL_ERROR);
        }
    }

    @Override
    public EnglishQuestionEntity saveQuestion(EnglishQuestionEntity question) {
        return repository.save(question);
    }


    @Override
    public void deleteQuestion(EnglishQuestionEntity question) {
        repository.delete(question);
    }

    @Override
    public Optional<EnglishQuestionEntity> getQuestionById(ObjectId id) {
        return repository.findById(id);
    }

}
