package com.qlish.qlish_api.practice_test;

import org.bson.types.ObjectId;

import java.util.List;

public interface TestService {

   List<TestEntity> findTestsByUserId(ObjectId userId);

    TestEntity findTestById(ObjectId id);

    void save(TestEntity testEntity);

    void delete(TestEntity testEntity);


}
