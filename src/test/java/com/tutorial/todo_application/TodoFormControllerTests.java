package com.tutorial.todo_application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.todo_application.controllers.TodoFormController;
import com.tutorial.todo_application.models.TodoItem;
import com.tutorial.todo_application.service.TodoItemService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = TodoFormController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoFormControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoItemService todoItemService;

    @Autowired
    private ObjectMapper objectMapper;

    TodoItem todoItem;

    @BeforeEach
    public void setUp(){
        todoItem = new TodoItem();
        todoItem.setDescription("Testing");
        todoItem.setId(1L);
        todoItem.setIsComplete(false);
    }

    //Post controller
    @Test
    @Order(1)
    @Disabled("Fungerer ikke med current setup av Post controller")
    public void saveTodoItemTest() throws Exception{
        //Precondition
        given(todoItemService.save(any(TodoItem.class))).willReturn(todoItem); //Konfigurerer Mockito til å returnere todoItem når todoItemService.save blir kalt med et hvilket som helst objekt av typen TodoItem.

        //Action
        ResultActions response = mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoItem)));

        //Verify
        response.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));

        ResultActions redirectResponse = mockMvc.perform(get("/"));

        redirectResponse.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(todoItem.getDescription())))
                .andExpect(jsonPath("$.id", is(todoItem.getId())));


    }
}
