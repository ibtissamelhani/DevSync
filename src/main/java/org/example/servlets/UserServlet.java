package org.example.servlets;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.User;
import org.example.repository.implementation.UserRepositoryImpl;
import org.example.repository.interfaces.UserRepository;
import org.example.service.UserService;

import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
    UserRepository userRepository = new UserRepositoryImpl(entityManagerFactory);
    UserService userService = new UserService(userRepository);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);

        // Forward request to the JSP page
        request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
    }
}
