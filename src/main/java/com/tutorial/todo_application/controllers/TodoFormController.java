package com.tutorial.todo_application.controllers;

import com.tutorial.todo_application.models.TodoItem;
import com.tutorial.todo_application.service.TodoItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TodoFormController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping("/create-todo")
    public String showCreateForm(TodoItem todoItem){
        return "new-todo-item"; //Denne dirigerer til en ny html side (new-todo-item)
    }

    @PostMapping("/todo")
    public String createTodoItem(@Valid TodoItem todoItem, BindingResult result, Model model){

        TodoItem item = new TodoItem();
        item.setDescription(todoItem.getDescription());
        item.setIsComplete(todoItem.getIsComplete());
        todoItemService.save(todoItem);

        return "redirect:/"; //Sender tilbake til url root av applikasjonen
    }

    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id, Model model){
        TodoItem todoItem = todoItemService.getById(id).orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + "Not found"));

        todoItemService.delete(todoItem);
        return "redirect:/";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model){
        TodoItem todoItem = todoItemService.getById(id).orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + "Not found"));

        model.addAttribute("todo", todoItem ); //Her blir det lagret med tagg "todo" og kan da bli hentet inni edit-todo-item.html
        return "edit-todo-item";
    }

    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") Long id, @Valid TodoItem todoItem, BindingResult result, Model model){
        TodoItem item = todoItemService.getById(id).orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + "Not found"));

        item.setIsComplete(todoItem.getIsComplete());
        item.setDescription(todoItem.getDescription());

        todoItemService.save(item);

        return "redirect:/";
    }
}
