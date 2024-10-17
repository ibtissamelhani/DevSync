package org.example.service;

import org.example.exception.TaskNotFoundException;
import org.example.model.entities.Tag;
import org.example.model.entities.Task;
import org.example.model.entities.User;
import org.example.repository.interfaces.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        @Test
    void TaskService_create_succeed() {

        //Given
        String[] tagIds = {"1", "2"};
        Long assigneeId = 1L;
        Task task = new Task();
        task.setTitle("New Task");
        task.setCreationDate(LocalDate.now().plusDays(3));
        task.setDueDate(LocalDate.now().plusDays(6));
        task.setDescription("description");

        Tag tag1 = new Tag();
        tag1.setId(1L);
        Tag tag2 = new Tag();
        tag2.setId(2L);

        User assignee = new User();
        assignee.setId(assigneeId);

        // When
        when(tagService.findById(1L)).thenReturn(Optional.of(tag1));
        when(tagService.findById(2L)).thenReturn(Optional.of(tag2));
        when(userService.getUserById(assigneeId)).thenReturn(Optional.of(assignee));
        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.create(task, tagIds, assigneeId);

        // Then
        verify(taskRepository).save(task);
        verify(tagService).findById(1L);
        verify(tagService).findById(2L);
        verify(userService).getUserById(assigneeId);
        assertEquals(2, createdTask.getTags().size());
        assertEquals(assigneeId, createdTask.getAssignee().getId());
    }

    @Test
    void TaskService_create_throwsIllegalArgumentException_whenTaskIsNull() {

        // Given
        String[] tagIds = {"1", "2"};
        Long assigneeId = 1L;

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> taskService.create(null, tagIds, assigneeId));

        assertEquals("Task cannot be null", exception.getMessage());

        verify(taskRepository,never()).save(null);
        verify(tagService,never()).findById(1L);
        verify(tagService,never()).findById(2L);
        verify(userService,never()).getUserById(assigneeId);
    }

    @Test
    void TaskService_create_CreateTaskWithNoAssignee() {

        // Given
        String[] tagIds = {"1", "2"};
        Task task = new Task();
        task.setTitle("New Task");
        task.setCreationDate(LocalDate.now().plusDays(3));
        task.setDueDate(LocalDate.now().plusDays(6));
        task.setDescription("description");

        Tag tag1 = new Tag();
        tag1.setId(1L);
        Tag tag2 = new Tag();
        tag2.setId(2L);

        // When
        when(tagService.findById(1L)).thenReturn(Optional.of(tag1));
        when(tagService.findById(2L)).thenReturn(Optional.of(tag2));
        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.create(task, tagIds, null);

        // Then
        verify(taskRepository).save(task);
        verify(tagService).findById(1L);
        verify(tagService).findById(2L);
        assertNull(createdTask.getAssignee());
        assertEquals(2, createdTask.getTags().size());
    }


}