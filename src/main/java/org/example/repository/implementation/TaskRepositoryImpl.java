package org.example.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import org.example.model.entities.Task;
import org.example.repository.interfaces.TaskRepository;

import java.util.List;
import java.util.Optional;

public class TaskRepositoryImpl implements TaskRepository {

    EntityManagerFactory entityManagerFactory;

    public TaskRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Boolean save(Task task) {
        boolean result = false;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(task);
            entityManager.getTransaction().commit();
            result = true;
        }catch (PersistenceException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error persisting task: " + e.getMessage());
            e.printStackTrace();
        }finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return result;
    }

    @Override
    public Optional<Task> findById(long id) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Task task = entityManager.find(Task.class, id);
            return Optional.ofNullable(task);
        }
    }

    @Override
    public List<Task> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("SELECT t FROM Task t", Task.class)
                    .getResultList();
        }
    }

    @Override
    public void delete(Task task) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            Task managedTask = entityManager.merge(task);
            entityManager.remove(managedTask);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void update(Task task) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try  {
            entityManager.getTransaction().begin();
            entityManager.merge(task);
            entityManager.getTransaction().commit();
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
