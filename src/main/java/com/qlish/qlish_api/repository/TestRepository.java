package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.model.TestEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends MongoRepository<TestEntity, ObjectId> {

    @Query("{ 'testDetails.userId': ?0 }")
    Optional<List<TestEntity>> findAllUserTests(ObjectId userId);

    @Override
    @NonNull
    Optional<TestEntity> findById(@NonNull ObjectId id);
}
