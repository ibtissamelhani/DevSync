package org.example.repository.interfaces;

import org.example.model.entities.Token;

import java.util.Optional;

public interface TokenRepository {

    Token save(Token token);
    Optional<Token> findSuppressionTokenByUserId(Long userId);
    Optional<Token> findModificationTokenByUserId(Long userId);
    Token updateToken(Token token);

}
