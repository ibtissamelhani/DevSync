package org.example.service;

import org.example.exception.InsufficientTokensException;
import org.example.exception.TaskNotFoundException;
import org.example.model.entities.Request;
import org.example.model.entities.Task;
import org.example.model.entities.User;
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
    private TaskService taskService;

    public RequestService(RequestRepository requestRepository, TokenService tokenService, TaskService taskService) {
        this.requestRepository = requestRepository;
        this.tokenService = tokenService;
        this.taskService = taskService;
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
            Long userId = updatedRequest.getUser().getId();

            if (updatedRequest.getType() == ActionType.DELETE) {
                tokenService.decrementToken(userId, TokenType.SUPPRESSION);
                taskService.delete(updatedRequest.getTask());
            } else if (updatedRequest.getType() == ActionType.SWAP) {
                tokenService.decrementToken(userId, TokenType.MODIFICATION);
            }
        }
        return updatedRequest;
    }

    public boolean handleTaskDeletionRequest(Long taskId, User loggedUser) throws TaskNotFoundException, InsufficientTokensException {
        Task task = taskService.findById(taskId).get();


        if (task.getCreator().getId() == loggedUser.getId()) {
            boolean deleted = taskService.delete(task);
            return deleted;
        }

        int suppressionTokens = tokenService.getSuppressionTokens(loggedUser);

        if (suppressionTokens > 0) {
            Request request = new Request(loggedUser, task, ActionType.DELETE);
            this.createRequest(request);
            return true;
        } else {
            throw new InsufficientTokensException("You do not have enough tokens to perform this action.");
        }
    }

}

