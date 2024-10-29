package com.dkbanas.todo.Application.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoItemRequestDTO {
    private String title;
    private String description;
    private Boolean completed;
}
