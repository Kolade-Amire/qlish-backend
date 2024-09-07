package com.qlish.qlish_api.english_question;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnglishQuestionRepository extends MongoRepository<EnglishQuestionEntity, ObjectId>, CustomEnglishQuestionRepository {


}
