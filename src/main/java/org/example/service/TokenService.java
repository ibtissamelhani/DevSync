package org.example.service;

import org.example.model.entities.Token;
import org.example.model.entities.User;
import org.example.repository.interfaces.TokenRepository;

import java.util.Optional;

public class TokenService {

    TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    public int getSuppressionTokens(User user) {
        Optional<Token> tokenOpt = tokenRepository.findSuppressionTokenByUserId(user.getId());
        if (tokenOpt.isPresent()) {
            Token token = tokenOpt.get();
            return token.getTokenCount();
        }
        return 0;
    }
}
