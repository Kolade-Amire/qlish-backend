package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByEmail(String email);
//    Optional<User> findUserByProfileNameIgnoreCase(String profileName);
    List<User> findTop20ByOrderByAllTimePointsDesc();

}
