package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.exception.QuestionsRetrievalException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CustomEnglishQuestionRepositoryImpl implements CustomEnglishQuestionRepository {

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
    public EnglishQuestionEntity saveQuestion(ObjectId id, EnglishQuestionEntity question) {

        return mongoTemplate.save(question);
    }

    @Override
    public <T extends Question> T updateQuestion(T question) {
        return saveQuestion(question.getId(), question);
    }

    @Override
    public void deleteQuestion(ObjectId id) {
        try {
            var question = mongoTemplate.findById(id, EnglishQuestionEntity.class);
            assert question != null;
            mongoTemplate.remove(question);
        } catch (Exception e) {
            throw new EntityNotFoundException("Question not found");
        }
    }

    @Override
    public EnglishQuestionEntity getQuestionById(ObjectId id) {
        return mongoTemplate.findById(id, EnglishQuestionEntity.class);
    }

}
