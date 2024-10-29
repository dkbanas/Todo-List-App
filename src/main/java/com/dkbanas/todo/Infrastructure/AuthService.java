package com.dkbanas.todo.Infrastructure;

import com.dkbanas.todo.Application.DTOs.LoginDTO;
import com.dkbanas.todo.Application.DTOs.RegisterDTO;
import com.dkbanas.todo.Application.Interfaces.IUserRepo;
import com.dkbanas.todo.Domain.Entities.RegisteredUser;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public void register(RegisterDTO input) {
        if (userRepo.findByEmail(input.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Account with this email already exists.");
        }
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setFullname(input.getFullname());
        registeredUser.setEmail(input.getEmail());
        registeredUser.setPassword(passwordEncoder.encode(input.getPassword()));
        registeredUser.setAuthorities(input.getAuthorities());

        userRepo.save(registeredUser);
    }

    public void login(LoginDTO input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }
}
