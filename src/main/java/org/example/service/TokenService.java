package org.example.service;

import org.example.model.entities.Token;
import org.example.repository.interfaces.TokenRepository;

public class TokenService {

    TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token save(Token token) {
        return tokenRepository.save(token);
    }
}
