package com.qlish.qlish_api.practice_test;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends MongoRepository<TestEntity, ObjectId> {

    Optional<List<TestEntity>> findAllByUserId(ObjectId userId);
}
