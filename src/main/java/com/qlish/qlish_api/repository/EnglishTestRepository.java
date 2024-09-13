package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.EnglishTest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnglishTestRepository extends MongoRepository<EnglishTest, ObjectId> {

    Optional<List<EnglishTest>> findAllByUserId(ObjectId userId);
    Optional<EnglishTest> findBy_id(ObjectId id);
}
