package com.qlish.qlish_api.service;

import com.qlish.qlish_api.entity.TokenEntity;
import com.qlish.qlish_api.exception.CustomQlishException;
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

    public TokenEntity findTokenByUserId(String userId) {
        return tokenRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException(String.format("Could not get token for user with id: %s ", userId)));
    }

    public void saveToken(TokenEntity token) {
        try {
            tokenRepository.save(token);
        } catch (Exception e) {
            throw new CustomQlishException("Failed to save token.", e);
        }
    }

    public void deleteTokenByUserId(String userId) {
        var token = findTokenByUserId(userId)
        tokenRepository.delete(token);
    }

}
