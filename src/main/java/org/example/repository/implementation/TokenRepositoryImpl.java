package org.example.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.model.entities.Token;
import org.example.model.enums.TokenType;
import org.example.repository.interfaces.TokenRepository;

import java.util.Optional;

public class TokenRepositoryImpl implements TokenRepository {

    EntityManagerFactory entityManagerFactory;

    public TokenRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Token save(Token token) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(token);
            entityManager.getTransaction().commit();
            return token;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Token> findSuppressionTokenByUserId(Long userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Token> query = entityManager.createQuery("SELECT t FROM Token t WHERE t.user.id = :userId AND t.type = :type", Token.class);
            query.setParameter("userId", userId);
            query.setParameter("type", TokenType.SUPPRESSION);
            return query.getResultList().stream().findFirst();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Token> findModificationTokenByUserId(Long userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Token> query = entityManager.createQuery("SELECT t FROM Token t WHERE t.user.id = :userId AND t.type = :type", Token.class);
            query.setParameter("userId", userId);
            query.setParameter("type", TokenType.MODIFICATION);
            return query.getResultList().stream().findFirst();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Token updateToken(Token token) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(token);
            entityManager.getTransaction().commit();
            return token;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
