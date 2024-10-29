package com.dkbanas.todo.Application.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoListRequestDTO {
    private String title;
    private String description;
    private Boolean completed;
}
