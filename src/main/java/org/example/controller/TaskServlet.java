package org.example.controller;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.exception.InsufficientTokensException;
import org.example.exception.TaskAlreadyExistException;
import org.example.exception.TaskNotFoundException;
import org.example.model.entities.Tag;
import org.example.model.entities.Task;
import org.example.model.entities.User;
import org.example.model.enums.TaskStatus;
import org.example.repository.implementation.*;
import org.example.repository.interfaces.TagRepository;
import org.example.repository.interfaces.TaskRepository;
import org.example.repository.interfaces.UserRepository;
import org.example.service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TaskServlet extends HttpServlet {

    TaskService taskService;
    TagService tagService;
    UserService userService;
    @Override
    public void init() throws ServletException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
        TaskRepository taskRepository = new TaskRepositoryImpl(entityManagerFactory);
        TagRepository tagRepository = new TagRepositoryImpl(entityManagerFactory);
        UserRepository userRepository = new UserRepositoryImpl(entityManagerFactory);
        TokenService tokenService = new TokenService(new TokenRepositoryImpl(entityManagerFactory));
        tagService = new TagService(tagRepository);
        userService = new UserService(userRepository,tokenService);
        taskService = new TaskService(taskRepository,tagService,userService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listTasks(request, response);
        } else if ("create".equals(action)) {
            showCreateForm(request, response);
        } else if ("details".equals(action)) {
              taskDetails(request, response);
        } else {
            listTasks(request, response);
        }
    }

    private void listTasks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> tasks = taskService.findAll();
        double notStartPercent = taskService.notStarPercent();
        double inProgPercent = taskService.inProgPercent();
        double compPercent = taskService.compPercent();
        request.setAttribute("tasks", tasks);
        request.setAttribute("notStartPercent", notStartPercent);
        request.setAttribute("inProgPercent", inProgPercent);
        request.setAttribute("compPercent", compPercent);
        request.getRequestDispatcher("/WEB-INF/views/dashboard/Task/tasks.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tag> tags = tagService.findAll();
        List<User> users = userService.getRegularUsers();
        request.setAttribute("tags", tags);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/dashboard/Task/createTaskForm.jsp").forward(request, response);
    }

    private void taskDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Optional<Task> opTask = taskService.findById(id);
        if (opTask.isPresent()) {
            Task task = opTask.get();
            req.setAttribute("task", task);
            req.getRequestDispatcher("/WEB-INF/views/dashboard/Task/taskDetails.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createTask(request, response);
        }else if ("editStatus".equals(action)) {
            editTask(request, response);
        }else if ("delete".equals(action)) {
            deleteTask(request, response);
        }
    }

    private void createTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User creator = (User) request.getSession().getAttribute("loggedUser");
        if (creator == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        LocalDate creationDate = LocalDate.parse(request.getParameter("creationDate"));
        LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));
        String[] tagIds = request.getParameterValues("tags[]");
        Long assigneeId = Long.valueOf(request.getParameter("assignee_id"));
        try {
            Task task = new Task(title,description,creationDate,dueDate, TaskStatus.NOT_STARTED, null,creator);
            Task savedTask = taskService.create(task, tagIds, assigneeId);
            if (savedTask != null) {
                response.sendRedirect("tasks?action=list");
            }else {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "task not created");
                response.sendRedirect("tasks?action=create");
            }

        } catch (TaskAlreadyExistException | IllegalArgumentException e) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect("tasks?action=create");
        }
    }

    private void editTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long taskId = Long.parseLong(request.getParameter("task_id"));
        String newStatus = request.getParameter("status");

        try {
            Optional<Task> opTask = taskService.findById(taskId);
            Task task = opTask.get();

            if (LocalDate.now().isAfter(task.getDueDate())) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot modify task after the due date.");
                return;
            }

            task.setStatus(TaskStatus.valueOf(newStatus));

           Task updatedTask = taskService.update(task);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0); // Proxies.
            response.sendRedirect(request.getContextPath() + "/users?action=taskDetails&id=" + updatedTask.getId());
        } catch (TaskNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the task.");
        }
    }


    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        if (loggedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        try {
            Optional<Task> opTask = taskService.findById(id);
            Task task = opTask.get();
            boolean result = taskService.delete(task);
            if (result) {
                req.getSession().setAttribute("message", "Task successfully deleted.");
            } else {
                req.getSession().setAttribute("errorMessage", "Failed to delete task. Try again later.");
            }

        } catch (TaskNotFoundException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
        }

        long userId = loggedUser.getId();
        resp.sendRedirect(req.getContextPath() + "/tasks?action=list");
    }

}
