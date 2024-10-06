package org.example.service;

import org.example.model.entities.Task;
import org.example.repository.interfaces.TaskRepository;

import java.util.List;
import java.util.Optional;

public class TaskService {

    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void create(Task task) {
        taskRepository.save(task);
    }

    public void update(Task task) {
        taskRepository.update(task);
    }

    public void delete(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        task.ifPresent(value -> taskRepository.delete(value));
    }

}
