package com.tutorial.todo_application;

import com.tutorial.todo_application.models.TodoItem;
import com.tutorial.todo_application.repository.TodoItemRepository;
import com.tutorial.todo_application.service.TodoItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TodoItemServiceTests {

    @InjectMocks
    private TodoItemService todoItemService;

    @Mock
    private TodoItemRepository todoItemRepository;

    @Test
    public void testGetTodoItemById(){
        Long id = 1L;
        TodoItem mockTodoItem = new TodoItem();
        mockTodoItem.setDescription("Test");
        mockTodoItem.setId(id);

        Mockito.when(todoItemRepository.findById(id)).thenReturn(Optional.of(mockTodoItem));

        Optional<TodoItem> result = todoItemService.getById(id);

        assertNotNull(result);
        assertEquals(id, result.get().getId());
        assertEquals("Test", result.get().getDescription());
    }
}
