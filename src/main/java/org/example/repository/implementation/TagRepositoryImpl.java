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

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public TagRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void save(Tag tag) {
        try  {
            entityManager.getTransaction().begin();
            entityManager.persist(tag);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Tag> findById(Long id) {
        try {
            Tag tag = entityManager.find(Tag.class, id);
            return Optional.ofNullable(tag);
        }  catch (NoResultException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findAll() {
        try {
            return entityManager.createQuery("SELECT t FROM Tag t", Tag.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Tag tag) {
        try  {
            entityManager.getTransaction().begin();
            entityManager.merge(tag);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(Tag tag) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(tag);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Tag> findByName(String name) {
        try {
            TypedQuery<Tag> query = entityManager.createQuery("SELECT t FROM Tag t WHERE t.name = :name", Tag.class);
            query.setParameter("name", name);
            Tag tag = query.getSingleResult();
            return Optional.ofNullable(tag);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
