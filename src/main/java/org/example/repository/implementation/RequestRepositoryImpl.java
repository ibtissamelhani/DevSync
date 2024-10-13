package org.example.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.model.entities.Request;
import org.example.repository.interfaces.RequestRepository;

public class RequestRepositoryImpl implements RequestRepository {

    private final EntityManagerFactory entityManagerFactory;

    public RequestRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Request save(Request request) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(request);
            entityManager.getTransaction().commit();
            return request;
        }
    }
}
