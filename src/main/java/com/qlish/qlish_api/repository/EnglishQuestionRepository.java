package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnglishQuestionRepository extends MongoRepository<EnglishQuestionEntity, ObjectId>, CustomEnglishQuestionRepository, QuestionRepository<EnglishQuestionEntity> {

    @Override
    List<EnglishQuestionEntity> getTestQuestions(EnglishModifier modifier, int size);

}
