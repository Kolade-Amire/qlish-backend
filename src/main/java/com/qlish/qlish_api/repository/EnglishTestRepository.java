package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.entity.EnglishTest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnglishTestRepository extends MongoRepository<EnglishTest, ObjectId> {

    @Query("{ 'testDetails.userId': ?0 }")
    Optional<List<EnglishTest>> findAllUserTests(ObjectId userId);


}
