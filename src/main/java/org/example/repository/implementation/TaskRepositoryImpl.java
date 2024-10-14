package org.example.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import org.example.model.entities.Tag;
import org.example.model.entities.Task;
import org.example.repository.interfaces.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepositoryImpl implements TaskRepository {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager ;


    public TaskRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public Task save(Task task) {
        try {
            entityManager.getTransaction().begin();
//            entityManager.persist(task);
//            entityManager.getTransaction().commit();
//            return task;
            List<Tag> mergedTags = new ArrayList<>();
            for (Tag tag : task.getTags()) {
                mergedTags.add(entityManager.merge(tag));  // Merge existing tags
            }
            task.setTags(mergedTags);

            if (task.getCreator() != null) {
                task.setCreator(entityManager.merge(task.getCreator()));  // Merge the creator
            }

            if (task.getAssignee() != null) {
                task.setAssignee(entityManager.merge(task.getAssignee()));  // Merge the assignee
            }

            // Merge the task itself (since it may also be detached)
            Task savedTask = entityManager.merge(task);

            entityManager.getTransaction().commit();
            return savedTask;
        }catch (PersistenceException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error persisting task: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Task> findById(long id) {
        try {
            Task task = entityManager.find(Task.class, id);
            return Optional.ofNullable(task);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> findAll() {
        try {
            return entityManager.createQuery("SELECT t FROM Task t JOIN fetch t.assignee join fetch t.creator", Task.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean delete(Task task) {
        try {
            entityManager.getTransaction().begin();
                task.getTags().clear();
                entityManager.persist(task);
                entityManager.remove(task);

            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void update(Task task) {
        try  {
            entityManager.getTransaction().begin();
            entityManager.merge(task);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            throw e;
        }
    }

    @Override
    public List<Task> findByAssigneeId(Long userId) {
        List<Task> tasks = List.of();
        try {
            entityManager.getTransaction().begin();

            tasks = entityManager.createQuery(
                            "SELECT t FROM Task t WHERE t.assignee.id = :userId", Task.class)
                    .setParameter("userId", userId)
                    .getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return tasks;
    }

}
