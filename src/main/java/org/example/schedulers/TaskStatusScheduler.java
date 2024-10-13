package org.example.schedulers;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import org.example.model.entities.Task;
import org.example.model.enums.TaskStatus;
import org.example.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public class TaskStatusScheduler {
    private static final Logger logger = Logger.getLogger(TaskStatusScheduler.class.getName());

    @Inject
    private TaskService taskService;


    @Schedule(hour = "0", minute = "0", second = "0", persistent = false)
    public void updateTaskStatuses() {
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
