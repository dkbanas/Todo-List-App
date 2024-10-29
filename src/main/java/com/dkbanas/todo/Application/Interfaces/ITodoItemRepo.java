package com.dkbanas.todo.Application.Interfaces;

import com.dkbanas.todo.Domain.Entities.TodoItem;
import com.dkbanas.todo.Domain.Entities.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITodoItemRepo extends JpaRepository<TodoItem, Integer> {
    List<TodoItem> findByTodoList(TodoList todoList);
}
