package org.example.schedulers;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.entities.Task;
import org.example.model.enums.TaskStatus;
import org.example.repository.implementation.*;
import org.example.repository.interfaces.TagRepository;
import org.example.repository.interfaces.TaskRepository;
import org.example.repository.interfaces.UserRepository;
import org.example.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Logger;
@Singleton
public class TaskStatusScheduler extends TimerTask {
    private static final Logger logger = Logger.getLogger(TaskStatusScheduler.class.getName());


    private TaskService taskService;

    public TaskStatusScheduler() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
        TaskRepository taskRepository = new TaskRepositoryImpl(entityManagerFactory);
        TagRepository tagRepository = new TagRepositoryImpl(entityManagerFactory);
        UserRepository userRepository = new UserRepositoryImpl(entityManagerFactory);
        TokenService tokenService = new TokenService(new TokenRepositoryImpl(entityManagerFactory));
        TagService tagService = new TagService(tagRepository);
        UserService userService = new UserService(userRepository,tokenService);
        this.taskService = new TaskService(taskRepository, tagService, userService);
    }

    @Override
    public void run() {
        logger.info("Running scheduled task to update task statuses...");

        List<Task> tasks = taskService.findAll();
        LocalDate today = LocalDate.now();

        for (Task task : tasks) {
            LocalDate taskEndDate = task.getDueDate();

            if (taskEndDate != null && taskEndDate.isBefore(today) && task.getStatus() != TaskStatus.COMPLETED) {
                task.setStatus(TaskStatus.CANCELED);
                taskService.update(task);
                logger.info("Updated task ID " + task.getId() + " to CANCELED.");
            }
        }
    }
}
