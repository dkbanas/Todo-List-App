package com.dkbanas.todo.Application.Mapper;

import com.dkbanas.todo.Application.DTOs.TodoListPageResponseDTO;
import com.dkbanas.todo.Application.DTOs.TodoListRequestDTO;
import com.dkbanas.todo.Application.DTOs.TodoListResponseDTO;
import com.dkbanas.todo.Domain.Entities.TodoList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoListMapper {
    TodoList toEntity(TodoListRequestDTO dto);

    TodoListResponseDTO toResponseDTO(TodoList entity);

    List<TodoListResponseDTO> toResponseDTOList(List<TodoList> todoLists);

    default TodoListPageResponseDTO toPageResponseDTO(List<TodoList> todoLists, long total) {
        List<TodoListResponseDTO> responseDTOs = toResponseDTOList(todoLists);
        return new TodoListPageResponseDTO(responseDTOs, total);
    }

}