package com.dkbanas.todo.Shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppExceptions.UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(AppExceptions.UserNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AppExceptions.TodoListAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleTodoListAlreadyExistsException(AppExceptions.TodoListAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(AppExceptions.TodoListNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTodoListNotFoundException(AppExceptions.TodoListNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AppExceptions.TodoListUpdateException.class)
    public ResponseEntity<Map<String, String>> handleTodoListUpdateException(AppExceptions.TodoListUpdateException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(AppExceptions.TodoItemNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTodoItemNotFound(AppExceptions.TodoItemNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AppExceptions.TodoItemBelongsToListException.class)
    public ResponseEntity<Map<String, String>> handleTodoItemBelongsToList(AppExceptions.TodoItemBelongsToListException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AppExceptions.TodoItemRequestNullException.class)
    public ResponseEntity<Map<String, String>> handleTodoItemRequestNull(AppExceptions.TodoItemRequestNullException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
