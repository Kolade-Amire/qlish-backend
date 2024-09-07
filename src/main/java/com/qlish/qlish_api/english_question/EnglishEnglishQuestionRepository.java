package com.qlish.qlish_api.english_question;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnglishEnglishQuestionRepository extends MongoRepository<EnglishQuestionEntity, ObjectId>, CustomEnglishQuestionRepository {

    @Query()
    Optional<List<EnglishQuestionEntity>> findByQuestionTopicOrQuestionClassOrLevel(String attribute);
}
