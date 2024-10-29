package com.dkbanas.todo.Mapper;

import com.dkbanas.todo.Application.DTOs.TodoItemRequestDTO;
import com.dkbanas.todo.Application.DTOs.TodoItemResponseDTO;
import com.dkbanas.todo.Application.Mapper.TodoItemMapper;
import com.dkbanas.todo.Domain.Entities.TodoItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TodoItemMapperTests {

    private final TodoItemMapper mapper = Mappers.getMapper(TodoItemMapper.class);

    @Test
    public void ShouldMapTodoItemRequestDtoToEntity() {
        // Arrange
        TodoItemRequestDTO requestDTO = new TodoItemRequestDTO();
        requestDTO.setTitle("New Task");
        requestDTO.setDescription("Task description");
        requestDTO.setCompleted(false);

        // Act
        TodoItem entity = mapper.toEntity(requestDTO);

        // Assert
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(requestDTO.getTitle(), entity.getTitle());
        Assertions.assertEquals(requestDTO.getDescription(), entity.getDescription());
        Assertions.assertEquals(requestDTO.getCompleted(), entity.getCompleted());
        Assertions.assertNotNull(entity.getCreatedAt());
        Assertions.assertNull(entity.getId());
    }

    @Test
    public void ShouldMapTodoItemEntityToResponseDto() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.now();
        TodoItem entity = new TodoItem();
        entity.setId(1);
        entity.setTitle("Existing Task");
        entity.setDescription("Existing task description");
        entity.setCompleted(true);
        entity.setCreatedAt(createdAt);

        // Act
        TodoItemResponseDTO responseDTO = mapper.toResponseDTO(entity);

        // Assert
        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(entity.getId(), responseDTO.getId());
        Assertions.assertEquals(entity.getTitle(), responseDTO.getTitle());
        Assertions.assertEquals(entity.getDescription(), responseDTO.getDescription());
        Assertions.assertEquals(entity.getCompleted(), responseDTO.getCompleted());
    }

    @Test
    public void ShouldMapTodoItemListToResponseDtoList() {
        // Arrange
        TodoItem entity1 = new TodoItem();
        entity1.setId(1);
        entity1.setTitle("Task 1");
        entity1.setDescription("Description 1");
        entity1.setCompleted(false);

        TodoItem entity2 = new TodoItem();
        entity2.setId(2);
        entity2.setTitle("Task 2");
        entity2.setDescription("Description 2");
        entity2.setCompleted(true);

        List<TodoItem> entities = Arrays.asList(entity1, entity2);

        // Act
        List<TodoItemResponseDTO> responseDTOs = mapper.toResponseDTOList(entities);

        // Assert
        Assertions.assertNotNull(responseDTOs);
        Assertions.assertEquals(entities.size(), responseDTOs.size());

        for (int i = 0; i < entities.size(); i++) {
            TodoItem entity = entities.get(i);
            TodoItemResponseDTO responseDTO = responseDTOs.get(i);

            Assertions.assertEquals(entity.getId(), responseDTO.getId());
            Assertions.assertEquals(entity.getTitle(), responseDTO.getTitle());
            Assertions.assertEquals(entity.getDescription(), responseDTO.getDescription());
            Assertions.assertEquals(entity.getCompleted(), responseDTO.getCompleted());
        }
    }

    @Test
    public void ShouldReturnEmptyListWhenToResponseDtoListGivenEmptyList() {
        // Arrange
        List<TodoItem> emptyEntities = Collections.emptyList();

        // Act
        List<TodoItemResponseDTO> responseDTOs = mapper.toResponseDTOList(emptyEntities);

        // Assert
        Assertions.assertNotNull(responseDTOs);
        Assertions.assertTrue(responseDTOs.isEmpty());
    }
}

