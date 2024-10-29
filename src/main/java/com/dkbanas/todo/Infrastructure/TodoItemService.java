package com.dkbanas.todo.Infrastructure;

import com.dkbanas.todo.Application.DTOs.TodoItemRequestDTO;
import com.dkbanas.todo.Application.Interfaces.ITodoItemRepo;
import com.dkbanas.todo.Application.Interfaces.ITodoItemService;
import com.dkbanas.todo.Application.Interfaces.ItodoListRepo;
import com.dkbanas.todo.Application.Interfaces.ItodoListService;
import com.dkbanas.todo.Application.Mapper.TodoItemMapper;
import com.dkbanas.todo.Domain.Entities.TodoItem;
import com.dkbanas.todo.Domain.Entities.TodoList;
import com.dkbanas.todo.Shared.AppExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoItemService implements ITodoItemService {

    private final ITodoItemRepo todoItemRepo;
    private final ItodoListRepo todoListRepo;
    private final ItodoListService todoListService;
    private final TodoItemMapper todoItemMapper;

    @Override
    public List<TodoItem> getItemsByTodoListId(Integer listId, Authentication authentication) {
        TodoList todoList = todoListService.getToDoListById(listId, authentication)
                .orElseThrow(() -> new AppExceptions.TodoListNotFoundException("Todo list not found."));
        return todoItemRepo.findByTodoList(todoList);
    }

    @Override
    public TodoItem getTodoItemByListIdAndItemId(Integer listId, Integer itemId, Authentication authentication) {
        TodoList todoList = todoListService.getToDoListById(listId, authentication)
                .orElseThrow(() -> new AppExceptions.TodoListNotFoundException("Todo list not found."));
        TodoItem todoItem = todoItemRepo.findById(itemId)
                .orElseThrow(() -> new AppExceptions.TodoItemNotFoundException("Todo item not found."));

        if (!todoItem.getTodoList().equals(todoList)) {
            throw new AppExceptions.TodoItemBelongsToListException("Todo item does not belong to the specified list.");
        }

        return todoItem;
    }

    @Override
    public TodoItem createTodoItem(TodoItemRequestDTO todoItemDTO, Integer listId, Authentication authentication) {
        if (todoItemDTO == null) {
            throw new AppExceptions.TodoItemRequestNullException("Todo item request cannot be null.");
        }

        TodoList todoList = todoListService.getToDoListById(listId, authentication)
                .orElseThrow(() -> new AppExceptions.TodoListNotFoundException("Todo list not found."));

        TodoItem todoItem = todoItemMapper.toEntity(todoItemDTO);

        if (todoItem.getCompleted() == null) {
            todoItem.setCompleted(false);
        }

        todoItem.setTodoList(todoList);
        return todoItemRepo.save(todoItem);
    }

    @Override
    public TodoItem updateTodoItem(Integer itemId, TodoItemRequestDTO todoItemDTO, Authentication authentication) {
        if (todoItemDTO == null) {
            throw new IllegalArgumentException("Todo item request cannot be null.");
        }

        TodoItem existingItem = todoItemRepo.findById(itemId)
                .orElseThrow(() -> new AppExceptions.TodoItemNotFoundException("Todo item not found."));

        existingItem.setTitle(todoItemDTO.getTitle());
        existingItem.setDescription(todoItemDTO.getDescription());
        existingItem.setCompleted(todoItemDTO.getCompleted());

        return todoItemRepo.save(existingItem);
    }

    @Override
    public void deleteTodoItem(Integer itemId, Authentication authentication) {
        TodoItem todoItem = todoItemRepo.findById(itemId)
                .orElseThrow(() -> new AppExceptions.TodoItemNotFoundException("Todo item not found."));
        todoItemRepo.delete(todoItem);
    }
}
