package com.dkbanas.todo.Presentation;

import com.dkbanas.todo.Application.DTOs.TodoItemRequestDTO;
import com.dkbanas.todo.Application.DTOs.TodoItemResponseDTO;
import com.dkbanas.todo.Application.Interfaces.ITodoItemService;
import com.dkbanas.todo.Application.Mapper.TodoItemMapper;
import com.dkbanas.todo.Domain.Entities.TodoItem;
import com.dkbanas.todo.Shared.AppExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/todolists/{listId}/items")
@RestController
@RequiredArgsConstructor
public class TodoItemController {

    @Autowired
    private ITodoItemService todoItemService;

    @Autowired
    private TodoItemMapper todoItemMapper;

    @GetMapping
    public ResponseEntity<List<TodoItemResponseDTO>> getItemsByTodoListId(
            @PathVariable Integer listId,
            Authentication authentication) {
        List<TodoItem> items = todoItemService.getItemsByTodoListId(listId, authentication);
        return ResponseEntity.ok(todoItemMapper.toResponseDTOList(items));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<TodoItemResponseDTO> getTodoItemByListIdAndItemId(
            @PathVariable Integer listId,
            @PathVariable Integer itemId,
            Authentication authentication) {
        TodoItem todoItem = todoItemService.getTodoItemByListIdAndItemId(listId, itemId, authentication);
        return ResponseEntity.ok(todoItemMapper.toResponseDTO(todoItem));
    }

    @PostMapping
    public ResponseEntity<TodoItemResponseDTO> createTodoItem(
            @PathVariable Integer listId,
            @RequestBody TodoItemRequestDTO todoItemRequestDTO,
            Authentication authentication) {
        TodoItem createdItem = todoItemService.createTodoItem(todoItemRequestDTO, listId, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoItemMapper.toResponseDTO(createdItem));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<TodoItemResponseDTO> updateTodoItem(
            @PathVariable Integer itemId,
            @RequestBody TodoItemRequestDTO todoItemRequestDTO,
            Authentication authentication) {
        TodoItem updatedItem = todoItemService.updateTodoItem(itemId, todoItemRequestDTO, authentication);
        return ResponseEntity.ok(todoItemMapper.toResponseDTO(updatedItem));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteTodoItem(
            @PathVariable Integer itemId,
            Authentication authentication) {
        todoItemService.deleteTodoItem(itemId, authentication);
        return ResponseEntity.noContent().build();
    }


}
