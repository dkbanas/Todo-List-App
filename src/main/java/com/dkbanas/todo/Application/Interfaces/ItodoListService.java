package com.dkbanas.todo.Application.Interfaces;

import com.dkbanas.todo.Application.DTOs.TodoListRequestDTO;
import com.dkbanas.todo.Domain.Entities.RegisteredUser;
import com.dkbanas.todo.Domain.Entities.TodoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface ItodoListService {
    RegisteredUser getAuthenticatedUser(Authentication authentication);
    Page<TodoList> getAllToDoLists(Authentication authentication, int page, int size, Sort sort);
    Optional<TodoList> getToDoListById(Integer id,Authentication authentication);
    TodoList createToDoList(TodoListRequestDTO todoListRequestDTO, Authentication authentication);
    TodoList updateToDoList(Integer id, TodoListRequestDTO todoListRequestDTO, Authentication authentication);
    void deleteToDoList(Integer id,Authentication authentication);
}
