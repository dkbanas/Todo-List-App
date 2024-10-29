package com.dkbanas.todo.Application.DTOs;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class LoginDTO {
    private String email;
    private String password;
}
