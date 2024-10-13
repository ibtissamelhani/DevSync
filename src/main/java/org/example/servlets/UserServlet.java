package org.example.servlets;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.User;
import org.example.model.enums.UserRole;
import org.example.repository.implementation.UserRepositoryImpl;
import org.example.repository.interfaces.UserRepository;
import org.example.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserServlet extends HttpServlet {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
    UserRepository userRepository = new UserRepositoryImpl(entityManagerFactory);
    UserService userService = new UserService(userRepository);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listUsers(request, response);
        } else if ("create".equals(action)) {
            showCreateForm(request, response);
        }else if ("edit".equals(action)) {
                showEditForm(request, response);
        } else if ("delete".equals(action)) {
            deleteUser(request, response);
        } else {
            listUsers(request, response);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.getAllUsers();

        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/createUserForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/editUserForm.jsp").forward(request, response);
        }else {
            response.sendRedirect(request.getContextPath() + "/users?action=list");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        userService.deleteUser(userId);
        response.sendRedirect(request.getContextPath() + "/users?action=list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createUser(request, response);
        }else if ("edit".equals(action)) {
            updateUser(request, response);
        }
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User newUser = new User(firstName,lastName,email,password,UserRole.valueOf(role));
        userService.createUser(newUser);
        response.sendRedirect(request.getContextPath() + "/users?action=list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String role = request.getParameter("role");


        User updatedUser = new User(firstName, lastName, email,password, UserRole.valueOf(role));
        updatedUser.setId(userId);
        userService.updateUser(updatedUser);
        response.sendRedirect(request.getContextPath() + "/users?action=list");
    }
}
