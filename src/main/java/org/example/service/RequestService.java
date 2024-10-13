package org.example.service;

import org.example.model.entities.Request;
import org.example.model.enums.ActionType;
import org.example.model.enums.RequestStatus;
import org.example.model.enums.TokenType;
import org.example.repository.interfaces.RequestRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestService {

    private RequestRepository requestRepository;
    private TokenService tokenService;

    public RequestService(RequestRepository requestRepository, TokenService tokenService) {
        this.requestRepository = requestRepository;
        this.tokenService = tokenService;
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
        Request updatedRequest = requestRepository.update(request);

        if (updatedRequest.getStatus() == RequestStatus.APPROVED) {
            Long userId = updatedRequest.getUser().getId(); // Get the user ID associated with the request

            // Handle token decrement based on the action type
            if (updatedRequest.getType() == ActionType.DELETE) {
                tokenService.decrementToken(userId, TokenType.SUPPRESSION); // Decrement suppression token
            } else if (updatedRequest.getType() == ActionType.SWAP) {
                tokenService.decrementToken(userId, TokenType.MODIFICATION); // Decrement modification token
            }
        }
        return updatedRequest;
    }
}

