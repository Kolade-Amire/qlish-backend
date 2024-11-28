package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.model.Question;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomQuestionRepository extends MongoRepository<Question, ObjectId> {

}
