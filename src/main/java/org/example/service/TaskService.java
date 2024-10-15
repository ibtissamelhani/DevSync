package org.example.service;

import org.example.exception.InsufficientTokensException;
import org.example.exception.TaskNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.model.entities.Request;
import org.example.model.entities.Tag;
import org.example.model.entities.Task;
import org.example.model.entities.User;
import org.example.model.enums.ActionType;
import org.example.repository.interfaces.TaskRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskService {

    TaskRepository taskRepository;
    TagService tagService;
    UserService userService;

    public TaskService(TaskRepository taskRepository, TagService tagService, UserService userService) {
        this.taskRepository = taskRepository;
        this.tagService = tagService;
        this.userService = userService;
    }

    public Optional<Task> findById(Long id) {

        Optional<Task> opTask = taskRepository.findById(id);
        if (opTask.isPresent()) {
            return opTask;
        }else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task create(Task task,String[] tagIds,Long assigneeId) {

        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        validateTaskForm(task.getTitle(),task.getCreationDate(),task.getDueDate(),tagIds,task.getDescription());

        List<Tag> selectedTags = new ArrayList<>();
        if (tagIds != null) {
            for (String tagId : tagIds) {
                Optional<Tag> tag = tagService.findById(Long.valueOf(tagId));
                tag.ifPresent(selectedTags::add);
            }
        }
        task.setTags(selectedTags);

        if (assigneeId != null) {
            Optional<User> user = userService.getUserById(assigneeId);
            user.ifPresent(task::setAssignee);
        } else {
            task.setAssignee(null);
        }
        return taskRepository.save(task);
    }

    public void update(Task task) {
        Optional<Task> opTask = taskRepository.findById(task.getId());
        if (opTask.isPresent()) {
            taskRepository.update(task);
        }else {
            throw new TaskNotFoundException("Task not found");
        }

    }

    public boolean delete(Task task) {
        Optional<Task> taskOptional = findById(task.getId());
        if (taskOptional.isPresent()) {
            Task taskToDelete = taskOptional.get();
            return taskRepository.delete(taskToDelete);
        }else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    private void validateTaskForm(String title, LocalDate creationDate, LocalDate dueDate, String[] tags, String description) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required.");
        }

        LocalDate today = LocalDate.now();
        LocalDate threeDaysAhead = today.plusDays(3);

        if (creationDate.isBefore(threeDaysAhead)) {
            throw new IllegalArgumentException("Creation date must be at least 3 days ahead.");
        }

        if (dueDate.isBefore(creationDate)) {
            throw new IllegalArgumentException("Due date cannot be earlier than creation date.");
        }

        if (tags == null || tags.length == 0) {
            throw new IllegalArgumentException("At least one tag is required.");
        }

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description is required.");
        }
    }

    public List<Task> getTaskByAssigneeId(Long assigneeId) {
        Optional<User> user = userService.getUserById(assigneeId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with ID " + assigneeId + " not found");
        }
        return taskRepository.findByAssigneeId(assigneeId);
    }
}
