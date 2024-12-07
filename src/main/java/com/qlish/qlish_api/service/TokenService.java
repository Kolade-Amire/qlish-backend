package com.qlish.qlish_api.service;

import com.qlish.qlish_api.model.TokenEntity;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.repository.RedisTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTokenRepository tokenRepository;

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
        var token = findTokenByUserId(userId);
        tokenRepository.delete(token);
    }

}
