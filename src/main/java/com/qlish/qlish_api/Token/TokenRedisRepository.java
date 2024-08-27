package com.qlish.qlish_api.Token;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRedisRepository extends ListCrudRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findByToken(String userId);

    Optional<List<TokenEntity>> findAllByUserId(String userId);

}
