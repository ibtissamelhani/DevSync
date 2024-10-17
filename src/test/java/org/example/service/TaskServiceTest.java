package org.example.service;

import org.example.exception.TaskNotFoundException;
import org.example.model.entities.Task;
import org.example.repository.interfaces.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    TagService tagService;

    @Mock
    UserService userService;

    @InjectMocks
    TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void TaskService_findById_returnsTask() {

        // Given
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);

        // When
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        Optional<Task> foundTask = taskService.findById(taskId);

        // Then
        verify(taskRepository).findById(taskId);
        assertTrue(foundTask.isPresent());
        assertEquals(taskId, foundTask.get().getId());
    }

    @Test
    void TaskService_findById_throwsTaskNotFoundException() {
        // Given
        long taskId = 1L;

        // When & Then
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(TaskNotFoundException.class,
                () -> taskService.findById(taskId));

        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository).findById(taskId);
    }

}