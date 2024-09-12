package com.qlish.qlish_api.service;

import com.qlish.qlish_api.entity.TokenEntity;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.repository.TokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRedisRepository tokenRepository;

    public TokenEntity getTokenByTokenValue(String token) {
        return tokenRepository.findByToken(token).orElseThrow(
                () -> new EntityNotFoundException("Token not found.")
        );
    }

    public Optional<TokenEntity> findTokenByUserId(String userId) {
        return tokenRepository.findByUserId(userId);
    }

    public void saveToken(TokenEntity token) {
        tokenRepository.save(token);
    }

    public void deleteTokenByUserId(String userId) {
        findTokenByUserId(userId).ifPresent(tokenRepository::delete);
    }

}
