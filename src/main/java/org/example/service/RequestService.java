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

    public Request updateRequest(Request request, User user) {
        Request updatedRequest = requestRepository.update(request);

        if (updatedRequest.getStatus() == RequestStatus.APPROVED) {
            Long userId = updatedRequest.getUser().getId();

            if (updatedRequest.getType() == ActionType.DELETE) {
                tokenService.decrementToken(userId, TokenType.SUPPRESSION);
                Task task = updatedRequest.getTask();
                taskService.delete(task);
            } else if (updatedRequest.getType() == ActionType.SWAP) {
                tokenService.decrementToken(userId, TokenType.MODIFICATION);
                Task task = updatedRequest.getTask();
                task.setAssignee(user);
                task.setTokenApplied(true);
                taskService.update(task);
            }
        }
        return updatedRequest;
    }

    public boolean handleTaskDeletionRequest(Long taskId, User loggedUser) throws TaskNotFoundException, InsufficientTokensException {
        Task task = taskService.findById(taskId).get();

        int suppressionTokens = tokenService.getSuppressionTokensCount(loggedUser);

        if (suppressionTokens > 0) {
            taskService.delete(task);
            tokenService.decrementToken(loggedUser.getId(), TokenType.SUPPRESSION);
            return true;
        } else {
            throw new InsufficientTokensException("You do not have enough tokens to perform this action.");
        }
    }

    public boolean handleTaskSwapRequest(Long taskId, User loggedUser) throws TaskNotFoundException, InsufficientTokensException {

        Task task = taskService.findById(taskId).get();

        int modificationTokens = tokenService.getModificationTokensCount(loggedUser);

        tokenService.decrementToken(loggedUser.getId(), TokenType.MODIFICATION);
        if (modificationTokens > 0) {
            Request request = new Request(loggedUser, task, ActionType.SWAP);
            this.createRequest(request);
            return true;
        } else {
            throw new InsufficientTokensException("You do not have enough tokens to perform this action.");
        }
    }

    public List<Request> findRequestsByStatus(RequestStatus status) {
        return requestRepository.findRequestsByStatus(status);
    }


}

