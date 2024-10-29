package com.dkbanas.todo.Application.Mapper;

import com.dkbanas.todo.Application.DTOs.TodoItemRequestDTO;
import com.dkbanas.todo.Application.DTOs.TodoItemResponseDTO;
import com.dkbanas.todo.Domain.Entities.TodoItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoItemMapper {
    TodoItem toEntity(TodoItemRequestDTO dto);

    TodoItemResponseDTO toResponseDTO(TodoItem entity);

    List<TodoItemResponseDTO> toResponseDTOList(List<TodoItem> items);

}
