package org.example.exception;

public class TaskAlreadyExistException extends RuntimeException {

    public TaskAlreadyExistException(String message) {
        super(message);
    }
}
