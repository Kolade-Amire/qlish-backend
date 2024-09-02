package com.qlish.qlish_api.security.Token;

import com.qlish.qlish_api.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return tokenRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Token not found.")
        );
    }

    public void saveToken(TokenEntity token) {
        tokenRepository.save(token);
    }

}
