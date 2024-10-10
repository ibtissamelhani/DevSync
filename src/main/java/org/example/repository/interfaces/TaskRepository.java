package org.example.repository.interfaces;

import org.example.model.entities.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);
    Optional<Task> findById(long id);
    List<Task> findAll();
    void delete(Task task);
    void update(Task task);
}
