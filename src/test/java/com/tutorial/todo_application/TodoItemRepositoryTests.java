package com.tutorial.todo_application;

import com.tutorial.todo_application.models.TodoItem;
import com.tutorial.todo_application.repository.TodoItemRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

//Unit Testing
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //@TestMethodOrder(MethodOrderer.OrderAnnotation.class) specify the order of test execution based on the order specified by the @Order annotation.
public class TodoItemRepositoryTests {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Test
    @DisplayName("Test 1: Save new todo task")
    @Order(1)
    @Rollback(value = false)
    public void saveNewTodoItemTest(){

        //Action
        TodoItem todoItem = new TodoItem();
        todoItem.setId(1L);
        todoItem.setDescription("Testing");

        todoItemRepository.save(todoItem);

        //Verify
        assertThat(todoItem.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getTodoItemTest(){
        //Action
        TodoItem todoItem = todoItemRepository.findById(1L).get();
        //Verify
        assertThat(todoItem.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getListOfTodoItemsTest(){
        //Action
        List<TodoItem> list = todoItemRepository.findAll();
        //Verify
        System.out.println(list);
        assertThat(list.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateTodoItemTest(){
        TodoItem todoItem = todoItemRepository.findById(1L).get();
        todoItem.setDescription("New description");
        TodoItem updatedItem = todoItemRepository.save(todoItem);

        //Verify
        assertThat(updatedItem.getDescription()).isEqualTo("New description");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteTodoItemTest(){
        //Action
        todoItemRepository.deleteById(1L);
        Optional<TodoItem> todoItem = todoItemRepository.findById(1L);

        //Verify
        assertThat(todoItem).isEmpty();
    }
}
