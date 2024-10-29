package com.dkbanas.todo.Application.DTOs;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Builder
@Setter
public class RegisterDTO {
    private String fullname;
    private String email;
    private String password;
    private List<String> authorities;
}
