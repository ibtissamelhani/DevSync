package org.example.service;

import org.example.model.entities.Token;
import org.example.model.entities.User;
import org.example.model.enums.TokenType;
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
    public int getModificationTokens(User user) {
        Optional<Token> tokenOpt = tokenRepository.findModificationTokenByUserId(user.getId());
        if (tokenOpt.isPresent()) {
            Token token = tokenOpt.get();
            return token.getTokenCount();
        }
        return 0;
    }

    public Token updateToken(Token token) {
        return tokenRepository.updateToken(token);
    }

    public void decrementToken(Long userId, TokenType tokenType) {
        Optional<Token> tokenOptional;

        if (tokenType == TokenType.SUPPRESSION) {
            tokenOptional = tokenRepository.findSuppressionTokenByUserId(userId);
        } else if (tokenType == TokenType.MODIFICATION) {
            tokenOptional = tokenRepository.findModificationTokenByUserId(userId);
        } else {
            throw new IllegalArgumentException("Unknown token type: " + tokenType);
        }

        tokenOptional.ifPresent(token -> {
            int currentCount = token.getTokenCount();
            if (currentCount > 0) {
                token.setTokenCount(currentCount - 1);
                this.updateToken(token);
            } else {
                throw new IllegalStateException("No " + tokenType + " tokens left for user " + userId);
            }
        });
    }

}
