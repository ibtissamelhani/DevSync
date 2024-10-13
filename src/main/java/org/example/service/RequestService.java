package org.example.service;

import org.example.model.entities.Request;
import org.example.model.entities.Task;
import org.example.model.entities.User;
import org.example.repository.interfaces.RequestRepository;

public class RequestService {

    private RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }
}
