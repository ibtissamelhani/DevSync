package org.example.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.model.entities.Tag;
import org.example.repository.interfaces.TagRepository;

import java.util.List;
import java.util.Optional;

public class TagRepositoryImpl implements TagRepository {

    private final EntityManagerFactory entityManagerFactory;

    public TagRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Tag tag) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try  {
            entityManager.getTransaction().begin();
            entityManager.persist(tag);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Tag> findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Tag tag = entityManager.find(Tag.class, id);
            return Optional.ofNullable(tag);
        }
    }

    @Override
    public List<Tag> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("SELECT t FROM Tag t", Tag.class)
                    .getResultList();
        }
    }

    @Override
    public void update(Tag tag) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try  {
            entityManager.getTransaction().begin();
            entityManager.merge(tag);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Tag tag) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            Tag managedTag = entityManager.merge(tag);
            entityManager.remove(managedTag);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Optional<Tag> findByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Tag> query = entityManager.createQuery("SELECT t FROM Tag t WHERE t.name = :name", Tag.class);
            query.setParameter("name", name);
            Tag tag = query.getSingleResult();
            return Optional.ofNullable(tag);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }
}
