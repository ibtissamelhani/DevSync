package org.example.repository.interfaces;

import org.example.model.entities.Request;
import org.example.model.enums.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface RequestRepository {

    Request save(Request request);
    List<Request> findAll();
    Optional<Request> findById(long id);
    Request update(Request request);
    List<Request> findRequestsByStatus(RequestStatus status);

}
