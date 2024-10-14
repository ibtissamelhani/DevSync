package org.example.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.model.entities.Request;
import org.example.model.entities.Tag;
import org.example.repository.interfaces.RequestRepository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Request> findById(long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Request request = entityManager.find(Request.class, id);
            return Optional.ofNullable(request);
        }
    }

    @Override
    public List<Request> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("SELECT r FROM Request r", Request.class)
                    .getResultList();
        }
    }

    @Override
    public Request update(Request request) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(request);
            entityManager.getTransaction().commit();
            return request;
        }
    }
}
