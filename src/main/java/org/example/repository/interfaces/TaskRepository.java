package org.example.repository.interfaces;

import org.example.model.entities.Task;

public interface TaskRepository {

    void save(Task task);
    Task findById(long id);
//    List<Task> findAll();
//    void delete(Task task);
//    void update(Task task);
}
