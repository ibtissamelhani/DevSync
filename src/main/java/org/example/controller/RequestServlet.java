package org.example.controller;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.Request;
import org.example.model.enums.RequestStatus;
import org.example.repository.implementation.RequestRepositoryImpl;
import org.example.repository.interfaces.RequestRepository;
import org.example.service.RequestService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/requests")
public class RequestServlet extends HttpServlet {

    RequestService requestService;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
        RequestRepository requestRepository = new RequestRepositoryImpl(entityManagerFactory);
        requestService = new RequestService(requestRepository);
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
    }
