package com.dkbanas.todo.Infrastructure;

import com.dkbanas.todo.Application.DTOs.TodoListRequestDTO;
import com.dkbanas.todo.Application.Interfaces.IUserRepo;
import com.dkbanas.todo.Application.Interfaces.ItodoListRepo;
import com.dkbanas.todo.Application.Interfaces.ItodoListService;
import com.dkbanas.todo.Application.Mapper.TodoListMapper;
import com.dkbanas.todo.Domain.Entities.RegisteredUser;
import com.dkbanas.todo.Domain.Entities.TodoList;
import com.dkbanas.todo.Shared.AppExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoListService implements ItodoListService {

    private final IUserRepo userRepo;
    private final ItodoListRepo todoListRepo;
    private final TodoListMapper todoListMapper;

    public RegisteredUser getAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new AppExceptions.UserNotFoundException("User not found"));
    }


    @Override
    public Page<TodoList> getAllToDoLists(Authentication authentication, int page, int size, Sort sort) {
        RegisteredUser currentUser = getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page, size, sort);
        return todoListRepo.findByOwner(currentUser, pageable);
    }

    @Override
    public Optional<TodoList> getToDoListById(Integer id, Authentication authentication) {
        RegisteredUser currentUser = getAuthenticatedUser(authentication);
        return Optional.ofNullable(todoListRepo.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new AppExceptions.TodoListNotFoundException("Todo list not found.")));
    }


    @Override
    public TodoList createToDoList(TodoListRequestDTO todoListRequestDTO, Authentication authentication) {
        RegisteredUser currentUser = getAuthenticatedUser(authentication);


        Optional<TodoList> existingList = todoListRepo.findByTitleAndOwner(todoListRequestDTO.getTitle(), currentUser);
        if (existingList.isPresent()) {
            throw new AppExceptions.TodoListAlreadyExistsException("Todo list with the same title already exists for this user.");
        }
        TodoList toDoList = todoListMapper.toEntity(todoListRequestDTO);
        toDoList.setOwner(currentUser);
        return todoListRepo.save(toDoList);
    }

    @Override
    public TodoList updateToDoList(Integer id, TodoListRequestDTO todoListRequestDTO, Authentication authentication) {
        RegisteredUser currentUser = getAuthenticatedUser(authentication);

        TodoList existingToDoList = todoListRepo.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new AppExceptions.TodoListNotFoundException("Todo list not found."));


        existingToDoList.setTitle(todoListRequestDTO.getTitle());
        existingToDoList.setDescription(todoListRequestDTO.getDescription());

        return todoListRepo.save(existingToDoList);
    }

    @Override
    public void deleteToDoList(Integer id, Authentication authentication) {
        RegisteredUser currentUser = getAuthenticatedUser(authentication);
        Optional<TodoList> toDoList = todoListRepo.findByIdAndOwner(id, currentUser);
        if (toDoList.isPresent()) {
            todoListRepo.delete(toDoList.get());
        } else {
            throw new AppExceptions.TodoListNotFoundException("Todo list not found.");
        }
    }
}
