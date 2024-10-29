package com.dkbanas.todo.Presentation;

import com.dkbanas.todo.Application.DTOs.TodoListPageResponseDTO;
import com.dkbanas.todo.Application.DTOs.TodoListRequestDTO;
import com.dkbanas.todo.Application.DTOs.TodoListResponseDTO;
import com.dkbanas.todo.Application.Interfaces.ItodoListService;
import com.dkbanas.todo.Application.Mapper.TodoListMapper;
import com.dkbanas.todo.Domain.Entities.TodoList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/todolists")
@RestController
@RequiredArgsConstructor
public class TodoListController {

    private final ItodoListService toDoListService;
    private final TodoListMapper todoListMapper;

    @GetMapping
    public ResponseEntity<TodoListPageResponseDTO> getAllToDoLists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            Authentication authentication) {

        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Page<TodoList> todoListPage = toDoListService.getAllToDoLists(authentication, page, size, sort);
        TodoListPageResponseDTO responseDTO = todoListMapper.toPageResponseDTO(todoListPage.getContent(), todoListPage.getTotalElements());

        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TodoListResponseDTO> getToDoListById(@PathVariable Integer id, Authentication authentication) {
        Optional<TodoList> todoList = toDoListService.getToDoListById(id, authentication);
        return todoList.map(list -> ResponseEntity.ok(todoListMapper.toResponseDTO(list)))
                .orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @PostMapping
    public ResponseEntity<TodoListResponseDTO> createToDoList(@RequestBody TodoListRequestDTO todoListRequestDTO, Authentication authentication) {
        TodoList createdList = toDoListService.createToDoList(todoListRequestDTO, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoListMapper.toResponseDTO(createdList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoListResponseDTO> updateToDoList(@PathVariable Integer id, @RequestBody TodoListRequestDTO todoListRequestDTO, Authentication authentication) {
        TodoList updatedList = toDoListService.updateToDoList(id, todoListRequestDTO, authentication);
        return ResponseEntity.ok(todoListMapper.toResponseDTO(updatedList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDoList(@PathVariable Integer id, Authentication authentication) {
        toDoListService.deleteToDoList(id, authentication);
        return ResponseEntity.noContent().build();
    }
}
