package org.example.controller;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.Tag;
import org.example.model.entities.Task;
import org.example.model.entities.User;
import org.example.model.enums.TaskStatus;
import org.example.repository.implementation.TagRepositoryImpl;
import org.example.repository.implementation.TaskRepositoryImpl;
import org.example.repository.interfaces.TagRepository;
import org.example.repository.interfaces.TaskRepository;
import org.example.service.TagService;
import org.example.service.TaskService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskServlet extends HttpServlet {

    TaskService taskService;
    TagService tagService;
    @Override
    public void init() throws ServletException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
        TaskRepository taskRepository = new TaskRepositoryImpl(entityManagerFactory);
        TagRepository tagRepository = new TagRepositoryImpl(entityManagerFactory);
        taskService = new TaskService(taskRepository);
        tagService = new TagService(tagRepository);
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
//            deleteTask(request, response);
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
        List<Tag> tags = tagService.findAll();
        request.setAttribute("tags", tags);
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
        String[] tagIds = request.getParameterValues("tags[]");

        List<Tag> selectedTags = new ArrayList<>();
        if (tagIds != null) {
            for (String tagId : tagIds) {
                Optional<Tag> tag = tagService.findById(Long.valueOf(tagId));
                tag.ifPresent(selectedTags::add);
            }
        }

        Task task = new Task(title,description,creationDate,dueDate, TaskStatus.NOT_STARTED, null,creator);
        task.setTags(selectedTags);
        boolean succeed = taskService.create(task);
        if (succeed){
            response.sendRedirect("tasks?action=list");
        }else {
            response.sendRedirect("tasks?action=create");
        }
    }

}
