package com.qlish.qlish_api.service;

import com.qlish.qlish_api.model.TokenEntity;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.repository.RedisTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);
    private final RedisTokenRepository tokenRepository;


    public TokenEntity findTokenByUserId(String userId) {
        return tokenRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException(String.format("Could not get token for user with id: %s ", userId)));
    }

    public TokenEntity findByToken(String token) {
        return tokenRepository.findByToken(token).orElseThrow(() -> new EntityNotFoundException("token not found"));
    }

    public void saveToken(TokenEntity token) {
        try {
            tokenRepository.save(token);
        } catch (Exception e) {
            throw new CustomQlishException("Failed to save token", e);
        }
    }

    public void deleteTokenByUserId(String userId) {
        try {
            var token = findTokenByUserId(userId);
            deleteToken(token);
        } catch (Exception e) {
            LOGGER.error("failed to delete token for user, {}", userId);
        }
    }

    public void deleteToken(TokenEntity token){
        try {
            tokenRepository.delete(token);
        } catch (Exception e) {
            LOGGER.error("token not found, failed to delete token");
        }
    }

}
