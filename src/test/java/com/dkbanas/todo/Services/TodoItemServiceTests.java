package com.dkbanas.todo.Services;

import com.dkbanas.todo.Application.DTOs.TodoItemRequestDTO;
import com.dkbanas.todo.Application.Interfaces.ITodoItemRepo;
import com.dkbanas.todo.Application.Interfaces.ItodoListRepo;
import com.dkbanas.todo.Application.Interfaces.ItodoListService;
import com.dkbanas.todo.Application.Mapper.TodoItemMapper;
import com.dkbanas.todo.Domain.Entities.RegisteredUser;
import com.dkbanas.todo.Domain.Entities.TodoItem;
import com.dkbanas.todo.Domain.Entities.TodoList;
import com.dkbanas.todo.Infrastructure.TodoItemService;
import com.dkbanas.todo.Shared.AppExceptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodoItemServiceTests {

    @InjectMocks
    private TodoItemService todoItemService;

    @Mock
    private ITodoItemRepo todoItemRepo;

    @Mock
    private ItodoListRepo todoListRepo;

    @Mock
    private ItodoListService todoListService;

    @Mock
    private TodoItemMapper todoItemMapper;

    @Mock
    private Authentication authentication;

    private RegisteredUser currentUser;
    private TodoList todoList;
    private TodoItem todoItem;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        currentUser = new RegisteredUser();
        currentUser.setEmail("user@example.com");

        todoList = new TodoList();
        todoList.setId(1);
        todoList.setTitle("Sample Todo List");

        todoItem = new TodoItem();
        todoItem.setId(1);
        todoItem.setTitle("Sample Todo Item");
        todoItem.setTodoList(todoList);
    }

    @Test
    public void shouldSuccessfullyGetItemsByTodoListId() {
        // Arrange
        when(authentication.getName()).thenReturn("user@example.com");
        when(todoListService.getToDoListById(1, authentication)).thenReturn(Optional.of(todoList));
        when(todoItemRepo.findByTodoList(todoList)).thenReturn(List.of(todoItem));

        // Act
        List<TodoItem> result = todoItemService.getItemsByTodoListId(1, authentication);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample Todo Item", result.get(0).getTitle());
    }

    @Test
    public void shouldThrowExceptionWhenGettingItemsForNonexistentList() {
        // Arrange
        when(authentication.getName()).thenReturn("user@example.com");
        when(todoListService.getToDoListById(1, authentication)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppExceptions.TodoListNotFoundException.class, () -> {
            todoItemService.getItemsByTodoListId(1, authentication);
        });
    }

    @Test
    public void shouldSuccessfullyGetTodoItemByListIdAndItemId() {
        // Arrange
        when(authentication.getName()).thenReturn("user@example.com");
        when(todoListService.getToDoListById(1, authentication)).thenReturn(Optional.of(todoList));
        when(todoItemRepo.findById(1)).thenReturn(Optional.of(todoItem));

        // Act
        TodoItem result = todoItemService.getTodoItemByListIdAndItemId(1, 1, authentication);

        // Assert
        assertNotNull(result);
        assertEquals("Sample Todo Item", result.getTitle());
    }

    @Test
    public void shouldThrowExceptionWhenTodoItemNotFound() {
        // Arrange
        when(authentication.getName()).thenReturn("user@example.com");
        when(todoListService.getToDoListById(1, authentication)).thenReturn(Optional.of(todoList));
        when(todoItemRepo.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppExceptions.TodoItemNotFoundException.class, () -> {
            todoItemService.getTodoItemByListIdAndItemId(1, 1, authentication);
        });
    }

    @Test
    public void shouldSuccessfullyCreateTodoItem() {
        // Arrange
        TodoItemRequestDTO requestDTO = new TodoItemRequestDTO();
        requestDTO.setTitle("New Todo Item");
        requestDTO.setDescription("Description");

        when(authentication.getName()).thenReturn("user@example.com");
        when(todoListService.getToDoListById(1, authentication)).thenReturn(Optional.of(todoList));

        TodoItem expectedTodoItem = new TodoItem();
        expectedTodoItem.setTitle("New Todo Item");
        expectedTodoItem.setDescription("Description");
        expectedTodoItem.setTodoList(todoList);

        when(todoItemMapper.toEntity(requestDTO)).thenReturn(expectedTodoItem);
        when(todoItemRepo.save(expectedTodoItem)).thenReturn(todoItem);

        // Act
        TodoItem result = todoItemService.createTodoItem(requestDTO, 1, authentication);

        // Assert
        assertNotNull(result);
        assertEquals(todoItem, result);
        verify(todoItemRepo).save(expectedTodoItem);
    }

    @Test
    public void shouldThrowExceptionWhenCreatingTodoItemWithNullRequest() {
        // Act & Assert
        assertThrows(AppExceptions.TodoItemRequestNullException.class, () -> {
            todoItemService.createTodoItem(null, 1, authentication);
        });
    }

    @Test
    public void shouldSuccessfullyUpdateTodoItem() {
        // Arrange
        TodoItem existingTodoItem = new TodoItem();
        existingTodoItem.setId(1);
        existingTodoItem.setTitle("Old Title");
        existingTodoItem.setDescription("Old Description");
        existingTodoItem.setCompleted(false);

        TodoItemRequestDTO requestDTO = new TodoItemRequestDTO();
        requestDTO.setTitle("Updated Todo Item");
        requestDTO.setDescription("Updated Description");
        requestDTO.setCompleted(true);

        // Mocking the calls
        when(todoItemRepo.findById(1)).thenReturn(Optional.of(existingTodoItem));
        when(todoItemRepo.save(existingTodoItem)).thenReturn(existingTodoItem);

        // Act
        TodoItem result = todoItemService.updateTodoItem(1, requestDTO, authentication);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Todo Item", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertTrue(result.getCompleted());

        verify(todoItemRepo).save(existingTodoItem);
        assertEquals("Updated Todo Item", existingTodoItem.getTitle());
        assertEquals("Updated Description", existingTodoItem.getDescription());
        assertTrue(existingTodoItem.getCompleted());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingTodoItemWithNullRequest() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            todoItemService.updateTodoItem(1, null, authentication);
        });
    }

    @Test
    public void shouldSuccessfullyDeleteTodoItem() {
        // Arrange
        when(todoItemRepo.findById(1)).thenReturn(Optional.of(todoItem));

        // Act
        assertDoesNotThrow(() -> {
            todoItemService.deleteTodoItem(1, authentication);
        });

        // Assert
        verify(todoItemRepo).delete(todoItem);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingNonexistentTodoItem() {
        // Arrange
        when(todoItemRepo.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppExceptions.TodoItemNotFoundException.class, () -> {
            todoItemService.deleteTodoItem(1, authentication);
        });
    }
}
