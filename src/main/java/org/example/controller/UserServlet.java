package org.example.controller;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.exception.TaskAlreadyExistException;
import org.example.exception.UserNotFoundException;
import org.example.model.entities.Tag;
import org.example.model.entities.Task;
import org.example.model.entities.User;
import org.example.model.enums.TaskStatus;
import org.example.model.enums.UserRole;
import org.example.repository.implementation.*;
import org.example.repository.interfaces.TaskRepository;
import org.example.repository.interfaces.UserRepository;
import org.example.service.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserServlet extends HttpServlet {


    UserService userService;
    TaskService taskService;
    TagService tagService;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
        UserRepository userRepository = new UserRepositoryImpl(entityManagerFactory);
        TaskRepository taskRepository = new TaskRepositoryImpl(entityManagerFactory);
        TokenService tokenService = new TokenService(new TokenRepositoryImpl(entityManagerFactory));
        tagService = new TagService(new TagRepositoryImpl(entityManagerFactory));
        userService = new UserService(userRepository,tokenService);
        taskService = new TaskService(taskRepository,tagService,userService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listUsers(request, response);
        } else if ("create".equals(action)) {
            showCreateForm(request, response);
        }else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else if ("login".equals(action)) {
            loginForm(request, response);
        } else if ("userInterface".equals(action)) {
           showUserInterface(request, response);
        }else if ("taskDetails".equals(action)) {
            showTaskDetails(request, response);
        }else if ("logout".equals(action)) {
            logout(request, response);
        }
        else {
            listUsers(request, response);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.getAllUsers();

        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/dashboard/User/users.jsp").forward(request, response);
    }

    private void showUserInterface(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        List<Task> tasks = taskService.getTaskByAssigneeId(id);
        List<Tag> tags = tagService.findAll();
        request.setAttribute("tasks", tasks);
        request.setAttribute("tags", tags);
        request.getRequestDispatcher("/WEB-INF/views/user/userInterface.jsp").forward(request, response);
    }
    private void showTaskDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Task> opTask = taskService.findById(id);
        Task task = opTask.get();
        request.setAttribute("task", task);
        request.getRequestDispatcher("/WEB-INF/views/user/taskDetails.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/dashboard/User/createUserForm.jsp").forward(request, response);
    }

    private void loginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/Auth/loginForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/users?action=list");
            return;
        }
        User user = optionalUser.get();
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/dashboard/User/editUserForm.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createUser(request, response);
        }else if ("edit".equals(action)) {
            updateUser(request, response);
        }else if ("login".equals(action)) {
            login(request, response);
        }else if ("selfAssign".equals(action)) {
            selfAssign(request, response);
        }else if ("delete".equals(action)) {
            deleteUser(request, response);
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

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long userId = Long.parseLong(request.getParameter("id"));
            Boolean isDeleted = userService.deleteUser(userId);

            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/users?action=list");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete user with id " + userId);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format");
        } catch (UserNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        userService.updateUser(userId,firstName,lastName,email,role);
        response.sendRedirect(request.getContextPath() + "/users?action=list");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userService.getUserByEmail(email);

            if (BCrypt.checkpw(password, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", user);
                checkRole(request, response, user);
            } else {
                response.sendRedirect(request.getContextPath() + "/users?action=login");
            }

        } catch (UserNotFoundException e) {
            response.sendRedirect(request.getContextPath() + "/users?action=login");
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/users?action=login");
    }

    private void checkRole(HttpServletRequest request, HttpServletResponse response,User user) throws ServletException, IOException {
        if(user.getRole().equals(UserRole.MANAGER)){
            response.sendRedirect(request.getContextPath() + "/tasks?action=list");
        }else if (user.getRole().equals(UserRole.USER)){
            Long userId = user.getId();
            response.sendRedirect(request.getContextPath() + "/users?action=userInterface&id="+userId);
        }else {
            response.sendRedirect(request.getContextPath() + "/users?action=login");
        }
    }

    private void selfAssign(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedUser = (User) request.getSession().getAttribute("loggedUser");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        LocalDate creationDate = LocalDate.parse(request.getParameter("creationDate"));
        LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));
        String[] tagIds = request.getParameterValues("tags[]");
        Long assigneeId = loggedUser.getId();
        try {
            Task task = new Task(title, description, creationDate, dueDate, TaskStatus.NOT_STARTED, null, loggedUser);
            taskService.create(task, tagIds, assigneeId);
            HttpSession session = request.getSession();
            session.setAttribute("message", "task added");
            response.sendRedirect("users?action=userInterface&id=" + loggedUser.getId());
        } catch (TaskAlreadyExistException | IllegalArgumentException e) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect("users?action=userInterface&id=" + loggedUser.getId());
        }
    }
}
