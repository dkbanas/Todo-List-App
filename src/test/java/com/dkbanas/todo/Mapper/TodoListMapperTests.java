package com.dkbanas.todo.Mapper;

import com.dkbanas.todo.Application.DTOs.TodoListPageResponseDTO;
import com.dkbanas.todo.Application.DTOs.TodoListRequestDTO;
import com.dkbanas.todo.Application.DTOs.TodoListResponseDTO;
import com.dkbanas.todo.Application.Mapper.TodoListMapper;
import com.dkbanas.todo.Domain.Entities.TodoList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TodoListMapperTests {
    private final TodoListMapper mapper = Mappers.getMapper(TodoListMapper.class);

    @Test
    public void ShouldMapRequestDtoToEntity(){
        //Arrange
        TodoListRequestDTO dto = new TodoListRequestDTO();
        dto.setTitle("title");
        dto.setDescription("description");

        //Act
        TodoList entity = mapper.toEntity(dto);

        //Assert
        Assertions.assertEquals(dto.getTitle(), entity.getTitle());
        Assertions.assertEquals(dto.getDescription(), entity.getDescription());
    }

    @Test
    public void ShouldMapEntityToResponseDto() throws Exception {
        // Arrange
        TodoList entity = new TodoList();
        entity.setId(1);
        entity.setTitle("title");
        entity.setDescription("description");
        LocalDateTime createdAt = LocalDateTime.now();
        entity.setCreatedAt(createdAt);

        // Act
        TodoListResponseDTO response = mapper.toResponseDTO(entity);

        // Assert
        Assertions.assertEquals(entity.getId(), response.getId());
        Assertions.assertEquals(entity.getTitle(), response.getTitle());
        Assertions.assertEquals(entity.getDescription(), response.getDescription());


        String expectedFormattedDate = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        String actualFormattedDate = response.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        Assertions.assertEquals(expectedFormattedDate, actualFormattedDate);
    }

    @Test
    public void ShouldMapEntityListToResponseDtoList() {
        // Arrange
        LocalDateTime createdAt1 = LocalDateTime.now();
        LocalDateTime createdAt2 = LocalDateTime.now().minusDays(1);

        TodoList entity1 = new TodoList();
        entity1.setId(1);
        entity1.setTitle("First Todo");
        entity1.setDescription("Description for first todo");
        entity1.setCreatedAt(createdAt1);

        TodoList entity2 = new TodoList();
        entity2.setId(2);
        entity2.setTitle("Second Todo");
        entity2.setDescription("Description for second todo");
        entity2.setCreatedAt(createdAt2);

        List<TodoList> entities = Arrays.asList(entity1, entity2);

        // Act
        List<TodoListResponseDTO> responseList = mapper.toResponseDTOList(entities);

        // Assert
        Assertions.assertEquals(entities.size(), responseList.size());

        for (int i = 0; i < entities.size(); i++) {
            TodoList entity = entities.get(i);
            TodoListResponseDTO response = responseList.get(i);

            Assertions.assertEquals(entity.getId(), response.getId());
            Assertions.assertEquals(entity.getTitle(), response.getTitle());
            Assertions.assertEquals(entity.getDescription(), response.getDescription());

            // Format the createdAt from the response and compare
            String expectedFormattedDate = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            String actualFormattedDate = response.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

            Assertions.assertEquals(expectedFormattedDate, actualFormattedDate);
        }
    }

    @Test
    public void ShouldReturnEmptyListWhenInputIsEmpty() {
        // Arrange
        List<TodoList> emptyEntities = Collections.emptyList();

        // Act
        List<TodoListResponseDTO> responseList = mapper.toResponseDTOList(emptyEntities);

        // Assert
        Assertions.assertTrue(responseList.isEmpty());
    }

    //Page
    @Test
    public void ShouldMapEntityListToPageResponseDto() {
        // Arrange
        LocalDateTime createdAt1 = LocalDateTime.now();
        LocalDateTime createdAt2 = LocalDateTime.now().minusDays(1);

        TodoList entity1 = new TodoList();
        entity1.setId(1);
        entity1.setTitle("First Todo");
        entity1.setDescription("Description for first todo");
        entity1.setCreatedAt(createdAt1);

        TodoList entity2 = new TodoList();
        entity2.setId(2);
        entity2.setTitle("Second Todo");
        entity2.setDescription("Description for second todo");
        entity2.setCreatedAt(createdAt2);

        List<TodoList> entities = Arrays.asList(entity1, entity2);
        long total = entities.size();

        // Act
        TodoListPageResponseDTO responsePage = mapper.toPageResponseDTO(entities, total);

        // Assert
        Assertions.assertNotNull(responsePage);
        Assertions.assertEquals(total, responsePage.getTotal());

        List<TodoListResponseDTO> responseDTOs = responsePage.getItems();
        Assertions.assertEquals(entities.size(), responseDTOs.size());

        for (int i = 0; i < entities.size(); i++) {
            TodoList entity = entities.get(i);
            TodoListResponseDTO response = responseDTOs.get(i);

            Assertions.assertEquals(entity.getId(), response.getId());
            Assertions.assertEquals(entity.getTitle(), response.getTitle());
            Assertions.assertEquals(entity.getDescription(), response.getDescription());


            String expectedFormattedDate = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            String actualFormattedDate = response.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

            Assertions.assertEquals(expectedFormattedDate, actualFormattedDate);
        }
    }

    @Test
    public void ShouldReturnEmptyPageWhenInputIsEmpty() {
        // Arrange
        List<TodoList> emptyEntities = Collections.emptyList();
        long total = 0;

        // Act
        TodoListPageResponseDTO responsePage = mapper.toPageResponseDTO(emptyEntities, total);

        // Assert
        Assertions.assertNotNull(responsePage);
        Assertions.assertEquals(total, responsePage.getTotal());
        Assertions.assertTrue(responsePage.getItems().isEmpty());
    }

}
