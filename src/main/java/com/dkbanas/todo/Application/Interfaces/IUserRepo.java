package com.dkbanas.todo.Application.Interfaces;




import com.dkbanas.todo.Domain.Entities.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepo extends JpaRepository<RegisteredUser, Integer> {
    Optional<RegisteredUser> findByEmail(String email);
}
