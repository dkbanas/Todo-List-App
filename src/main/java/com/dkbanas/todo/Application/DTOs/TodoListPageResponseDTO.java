package com.dkbanas.todo.Application.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoListPageResponseDTO {
    private List<TodoListResponseDTO> items;
    private long total;
}
