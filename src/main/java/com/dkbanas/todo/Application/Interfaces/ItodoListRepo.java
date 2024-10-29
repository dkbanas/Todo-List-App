package com.dkbanas.todo.Application.Interfaces;

import com.dkbanas.todo.Domain.Entities.RegisteredUser;
import com.dkbanas.todo.Domain.Entities.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItodoListRepo extends JpaRepository<TodoList, Integer> {
    Page<TodoList> findByOwner(RegisteredUser owner,Pageable pageable);
    Optional<TodoList> findByIdAndOwner(Integer id, RegisteredUser owner);
    Optional<TodoList> findByTitleAndOwner(String title, RegisteredUser owner);
}
