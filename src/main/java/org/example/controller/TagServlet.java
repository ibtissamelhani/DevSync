package org.example.controller;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entities.Tag;
import org.example.model.entities.Task;
import org.example.repository.implementation.TagRepositoryImpl;
import org.example.repository.interfaces.TagRepository;
import org.example.service.TagService;

import java.io.IOException;
import java.util.List;


public class TagServlet extends HttpServlet {

    TagService tagService;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
        TagRepository tagRepository = new TagRepositoryImpl(entityManagerFactory);
        tagService = new TagService(tagRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listTags(request, response);

        } else if ("delete".equals(action)) {
            deleteTag(request, response);
        } else {
        listTags(request, response);
        }
    }

    private void listTags(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tag> tags = tagService.findAll();
        request.setAttribute("tags", tags);
        request.getRequestDispatcher("/WEB-INF/views/dashboard/Tag/tags.jsp").forward(request, response);
    }

    private void deleteTag(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long tagId = Long.parseLong(request.getParameter("id"));
        tagService.deleteTag(tagId);
        response.sendRedirect(request.getContextPath() + "/tags?action=list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            String tagName = request.getParameter("name");
            Tag tag = new Tag(tagName);
            tagService.createTag(tag);
            response.sendRedirect("tags?action=list");
        }
    }
}
