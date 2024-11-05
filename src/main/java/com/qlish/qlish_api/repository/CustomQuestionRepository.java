package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.entity.CustomQuestion;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CustomQuestionRepository extends MongoRepository<CustomQuestion, ObjectId> {

    Page<CustomQuestion> getCustomQuestionByModifiers(Map<String, String> modifiers, Pageable pageable);
}
