package org.example.service;

import org.example.model.entities.Request;
import org.example.model.entities.Task;
import org.example.model.entities.User;
import org.example.model.enums.RequestStatus;
import org.example.repository.interfaces.RequestRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestService {

    private RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public List<Request> getPendingRequest() {
       return getAllRequests().stream()
               .filter(req -> req.getStatus().equals(RequestStatus.PENDING))
               .collect(Collectors.toList());
    }

    public Optional<Request> getRequestById(long id) {
        return requestRepository.findById(id);
    }

    public Request updateRequest(Request request) {
        return requestRepository.update(request);
    }
}
