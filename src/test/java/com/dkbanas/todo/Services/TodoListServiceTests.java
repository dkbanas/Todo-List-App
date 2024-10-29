package com.dkbanas.todo.Services;

import com.dkbanas.todo.Application.DTOs.TodoListRequestDTO;
import com.dkbanas.todo.Application.Interfaces.IUserRepo;
import com.dkbanas.todo.Application.Interfaces.ItodoListRepo;
import com.dkbanas.todo.Application.Interfaces.ItodoListService;
import com.dkbanas.todo.Application.Mapper.TodoListMapper;
import com.dkbanas.todo.Domain.Entities.RegisteredUser;
import com.dkbanas.todo.Domain.Entities.TodoList;
import com.dkbanas.todo.Infrastructure.TodoListService;
import com.dkbanas.todo.Shared.AppExceptions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodoListServiceTests {

    @InjectMocks
    private TodoListService listService;

    @Mock
    private ItodoListRepo listRepo;

    @Mock
    private TodoListMapper todoListMapper;

    @Mock
    private IUserRepo userRepo;

    @Mock
    private Authentication authentication;

    private RegisteredUser currentUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        currentUser = new RegisteredUser();
        currentUser.setEmail("user@example.com");
    }

    @Test
    public void shouldSuccessfullyReturnLists() {
        // Arrange
        when(authentication.getName()).thenReturn("user@example.com");
        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(currentUser));

        TodoList todoList1 = new TodoList();
        todoList1.setId(1);
        todoList1.setTitle("First Todo");

        TodoList todoList2 = new TodoList();
        todoList2.setId(2);
        todoList2.setTitle("Second Todo");

        List<TodoList> todoLists = List.of(todoList1, todoList2);
        Page<TodoList> page = new PageImpl<>(todoLists);
        int pageNumber = 0;
        int pageSize = 2;
        Sort sort = Sort.by("title");

        when(listRepo.findByOwner(currentUser, PageRequest.of(pageNumber, pageSize, sort))).thenReturn(page);

        // Act
        Page<TodoList> result = listService.getAllToDoLists(authentication, pageNumber, pageSize, sort);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("First Todo", result.getContent().get(0).getTitle());
        assertEquals("Second Todo", result.getContent().get(1).getTitle());
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWhenUserNotAuthenticated() {
        // Arrange
        when(authentication.getName()).thenReturn("user@example.com");
        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppExceptions.UserNotFoundException.class, () -> {
            listService.getAllToDoLists(authentication, 0, 10, Sort.unsorted());
        });
    }

    @Test
    public void shouldSuccessfullyReturnToDoListById() {
        // Arrange
        TodoList todoList = new TodoList();
        todoList.setId(1);
        todoList.setTitle("First Todo");

        when(authentication.getName()).thenReturn("user@example.com");
        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(currentUser));
        when(listRepo.findByIdAndOwner(1, currentUser)).thenReturn(Optional.of(todoList));

        // Act
        Optional<TodoList> result = listService.getToDoListById(1, authentication);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("First Todo", result.get().getTitle());
    }

    @Test
    public void shouldThrowTodoListNotFoundExceptionWhenToDoListNotFound() {
        // Arrange
        when(authentication.getName()).thenReturn("user@example.com");
        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(currentUser));
        when(listRepo.findByIdAndOwner(1, currentUser)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppExceptions.TodoListNotFoundException.class, () -> {
            listService.getToDoListById(1, authentication);
        });
    }

    @Test
    public void shouldSuccessfullyCreateToDoList() {
        // Arrange
        TodoListRequestDTO requestDTO = new TodoListRequestDTO();
        requestDTO.setTitle("New Todo");
        requestDTO.setDescription("Description for new todo");

        when(authentication.getName()).thenReturn("user@example.com");
        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(currentUser));
        when(listRepo.findByTitleAndOwner("New Todo", currentUser)).thenReturn(Optional.empty());

        TodoList todoList = new TodoList();
        todoList.setId(1);
        todoList.setTitle("New Todo");

        when(todoListMapper.toEntity(requestDTO)).thenReturn(todoList);
        when(listRepo.save(todoList)).thenReturn(todoList);

        // Act
        TodoList result = listService.createToDoList(requestDTO, authentication);

        // Assert
        assertNotNull(result);
        assertEquals("New Todo", result.getTitle());
        verify(listRepo).save(todoList);
    }

    @Test
    public void shouldThrowTodoListAlreadyExistsExceptionWhenCreatingDuplicateToDoList() {
        // Arrange
        TodoListRequestDTO requestDTO = new TodoListRequestDTO();
        requestDTO.setTitle("Duplicate Todo");

        when(authentication.getName()).thenReturn("user@example.com");
        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(currentUser));
        when(listRepo.findByTitleAndOwner("Duplicate Todo", currentUser)).thenReturn(Optional.of(new TodoList()));

        // Act & Assert
        assertThrows(AppExceptions.TodoListAlreadyExistsException.class, () -> {
            listService.createToDoList(requestDTO, authentication);
        });
    }

    @Test
    public void shouldSuccessfullyUpdateToDoList() {
        // Arrange
        TodoList existingTodoList = new TodoList();
        existingTodoList.setId(1);
        existingTodoList.setTitle("Old Title");
        existingTodoList.setDescription("Old Description");

        TodoListRequestDTO requestDTO = new TodoListRequestDTO();
        requestDTO.setTitle("Updated Title");
        requestDTO.setDescription("Updated Description");

        when(authentication.getName()).thenReturn("user@example.com");
        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(currentUser));
        when(listRepo.findByIdAndOwner(1, currentUser)).thenReturn(Optional.of(existingTodoList));
        when(listRepo.save(existingTodoList)).thenReturn(existingTodoList);  // Mocking save behavior

        // Act
        TodoList result = listService.updateToDoList(1, requestDTO, authentication);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        verify(listRepo).save(existingTodoList);
        assertEquals("Updated Title", existingTodoList.getTitle());
        assertEquals("Updated Description", existingTodoList.getDescription());
    }

    @Test
    public void shouldSuccessfullyDeleteToDoList() {
        // Arrange
        TodoList existingTodoList = new TodoList();
        existingTodoList.setId(1);
        existingTodoList.setTitle("To be deleted");

        when(authentication.getName()).thenReturn("user@example.com");
        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(currentUser));
        when(listRepo.findByIdAndOwner(1, currentUser)).thenReturn(Optional.of(existingTodoList));

        // Act
        listService.deleteToDoList(1, authentication);

        // Assert
        verify(listRepo).delete(existingTodoList);
    }

    @Test
    public void shouldThrowTodoListNotFoundExceptionWhenDeletingNonexistentToDoList() {
        // Arrange
        when(authentication.getName()).thenReturn("user@example.com");
        when(userRepo.findByEmail("user@example.com")).thenReturn(Optional.of(currentUser));
        when(listRepo.findByIdAndOwner(1, currentUser)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppExceptions.TodoListNotFoundException.class, () -> {
            listService.deleteToDoList(1, authentication);
        });
    }

}
