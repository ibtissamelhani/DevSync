package org.example.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.Task;
import org.example.model.entities.User;
import org.example.model.enums.TaskStatus;
import org.example.repository.implementation.TaskRepositoryImpl;
import org.example.repository.implementation.UserRepositoryImpl;
import org.example.repository.interfaces.TaskRepository;
import org.example.repository.interfaces.UserRepository;
import org.example.service.TaskService;
import org.example.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class TaskServlet extends HttpServlet {

    TaskService taskService;
    UserService userService;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
        TaskRepository taskRepository = new TaskRepositoryImpl(entityManagerFactory);
        UserRepository userRepository = new UserRepositoryImpl(entityManagerFactory);
        userService = new UserService(userRepository);
        taskService = new TaskService(taskRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listTasks(request, response);
        } else if ("create".equals(action)) {
            showCreateForm(request, response);
        }else if ("edit".equals(action)) {
//            showEditForm(request, response);
        } else if ("delete".equals(action)) {
//            deleteUser(request, response);
        } else {
            listTasks(request, response);
        }
    }

    private void listTasks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> tasks = taskService.findAll();
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("/WEB-INF/views/dashboard/Task/tasks.jsp").forward(request, response);
    }
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/dashboard/Task/createTaskForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createTask(request, response);
        }else if ("edit".equals(action)) {
            System.out.println();
        }
    }

    private void createTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User creator = (User) request.getSession().getAttribute("loggedUser");
        if (creator == null) {
            response.sendRedirect("tasks?action=list");
            return;
        }
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        LocalDate creationDate = LocalDate.parse(request.getParameter("creationDate"));
        LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));

        Task task = new Task(title,description,creationDate,dueDate, TaskStatus.NOT_STARTED, null,creator);
        boolean succeed = taskService.create(task);
        if (succeed){
            response.sendRedirect("tasks?action=list");
        }else {
            response.sendRedirect("tasks?action=create");
        }
    }

}
