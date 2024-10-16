package org.example.repository.implementation;

import jakarta.persistence.*;
import org.example.model.entities.Request;
import org.example.model.entities.Tag;
import org.example.model.enums.RequestStatus;
import org.example.repository.interfaces.RequestRepository;

import java.util.List;
import java.util.Optional;

public class RequestRepositoryImpl implements RequestRepository {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public RequestRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public Request save(Request request) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(request);
            entityManager.getTransaction().commit();
            return request;
        } catch (PersistenceException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return null;
        }
    }

    @Override
    public Optional<Request> findById(long id) {
        try {
            Request request = entityManager.find(Request.class, id);
            return Optional.ofNullable(request);
        }  catch (NoResultException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Request> findAll() {
        try {
            return entityManager.createQuery("SELECT r FROM Request r", Request.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Request update(Request request) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(request);
            entityManager.getTransaction().commit();
            return request;
        }catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Request> findRequestsByStatus(RequestStatus status) {
        try {
            TypedQuery<Request> query = entityManager.createQuery(
                    "SELECT r FROM Request r WHERE r.status = :status", Request.class);
            query.setParameter("status", status);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
