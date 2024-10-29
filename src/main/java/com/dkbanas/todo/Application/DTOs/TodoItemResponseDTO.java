package com.dkbanas.todo.Application.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoItemResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private Boolean completed;
}
