package com.dkbanas.todo.Application.Interfaces;

import com.dkbanas.todo.Application.DTOs.TodoItemRequestDTO;
import com.dkbanas.todo.Domain.Entities.TodoItem;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ITodoItemService {
    List<TodoItem> getItemsByTodoListId(Integer listId, Authentication authentication);
    TodoItem getTodoItemByListIdAndItemId(Integer listId, Integer itemId, Authentication authentication);
    TodoItem createTodoItem(TodoItemRequestDTO todoItem, Integer listId, Authentication authentication);
    TodoItem updateTodoItem(Integer itemId, TodoItemRequestDTO todoItem, Authentication authentication);
    void deleteTodoItem(Integer itemId, Authentication authentication);
}
