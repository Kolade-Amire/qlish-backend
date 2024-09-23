package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("english")
public interface EnglishQuestionRepository extends MongoRepository<EnglishQuestionEntity, ObjectId>, CustomEnglishQuestionRepository {
}