package com.qlish.qlish_api.security.token;

import org.bson.types.ObjectId;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRedisRepository extends ListCrudRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findByToken(String token);

    Optional<TokenEntity> findByUserId(String userId);

}
