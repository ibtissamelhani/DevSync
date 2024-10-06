package org.example.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.model.entities.Task;
import org.example.repository.interfaces.TaskRepository;

import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {

    EntityManagerFactory entityManagerFactory;

    public TaskRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Task task) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(task);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }finally {
            entityManager.close();
        }
    }

    @Override
    public Task findById(long id) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.find(Task.class, id) ;
        }
    }

//    @Override
//    public List<Task> findAll() {
//        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
//            TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t", Task.class);
//
//        }
//    }


}
