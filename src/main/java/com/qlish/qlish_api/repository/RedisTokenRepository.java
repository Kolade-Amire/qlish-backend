package com.qlish.qlish_api.repository;

import com.qlish.qlish_api.model.TokenEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisTokenRepository extends ListCrudRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findByToken(String token);

    Optional<TokenEntity> findByUserId(String userId);

}
