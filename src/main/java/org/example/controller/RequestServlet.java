package org.example.controller;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.InsufficientTokensException;
import org.example.exception.TaskNotFoundException;
import org.example.model.entities.Request;
import org.example.model.entities.Task;
import org.example.model.entities.User;
import org.example.model.enums.RequestStatus;
import org.example.repository.implementation.*;
import org.example.repository.interfaces.RequestRepository;
import org.example.service.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/requests")
public class RequestServlet extends HttpServlet {

    RequestService requestService;
    TokenService tokenService;
    TaskService taskService;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
        RequestRepository requestRepository = new RequestRepositoryImpl(entityManagerFactory);
        tokenService = new TokenService(new TokenRepositoryImpl(entityManagerFactory));
        TagService tagService = new TagService(new TagRepositoryImpl(entityManagerFactory));
        UserService userService = new UserService(new UserRepositoryImpl(entityManagerFactory),tokenService);
        taskService = new TaskService(new TaskRepositoryImpl(entityManagerFactory),tagService,userService);
        requestService = new RequestService(requestRepository,tokenService,taskService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Request> requests = requestService.getAllRequests();
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("/WEB-INF/views/dashboard/Request/requests.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("editStatus".equals(action)) {
            editRequest(request, response);
        }else if ("deleteRequest".equals(action)) {
            deleteRequest(request, response);
        }else if ("removeTask".equals(action)) {
            removeTask(request, response);
        }else if ("swapTask".equals(action)) {
            handleSwapRequest(request, response);
        }
    }

    private void editRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long requestId = Long.parseLong(request.getParameter("requestId"));
        String newStatus = request.getParameter("status");
        Optional<Request> optionalRequest = requestService.getRequestById(requestId);
        if (optionalRequest.isPresent()) {
            Request req  = optionalRequest.get();
            req.setStatus(RequestStatus.valueOf(newStatus));
            requestService.updateRequest(req);
            response.sendRedirect(request.getContextPath() + "/requests");
        }

    }

    private void deleteRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");

        if (loggedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            boolean result = requestService.handleTaskDeletionRequest(id, loggedUser);

            if (result) {
                req.getSession().setAttribute("message", "Done");
            } else {
                req.getSession().setAttribute("errorMessage", "Failed to delete task. Try again later.");
            }
        } catch (TaskNotFoundException e) {
            req.getSession().setAttribute("errorMessage", "Task not found.");
        } catch (InsufficientTokensException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
        }

        long userId = loggedUser.getId();
        resp.sendRedirect(req.getContextPath() + "/users?action=userInterface&id=" + userId);
    }

    private void removeTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            req.getSession().setAttribute("errorMessage", "Task not found.");
        }

        long userId = loggedUser.getId();
        resp.sendRedirect(req.getContextPath() + "/users?action=userInterface&id=" + userId);
    }

    private void handleSwapRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long taskId = Long.parseLong(req.getParameter("id"));
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");

        if (loggedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            boolean result = requestService.handleTaskSwapRequest(taskId, loggedUser);

            if (result) {
                req.getSession().setAttribute("message", "Swap request successfully submitted.");
            } else {
                req.getSession().setAttribute("errorMessage", "Failed to submit swap request. Try again later.");
            }
        } catch (TaskNotFoundException e) {
            req.getSession().setAttribute("errorMessage", "Task not found.");
        } catch (InsufficientTokensException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
        }

        long userId = loggedUser.getId();
        resp.sendRedirect(req.getContextPath() + "/users?action=userInterface&id=" + userId);
    }

}
