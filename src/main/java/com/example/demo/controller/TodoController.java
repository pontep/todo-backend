package com.example.demo.controller;

import com.example.demo.entity.Todo;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
        List<Todo> todos = this.todoService.getAllTodos();
        return ResponseEntity.ok().body(todos);
    }

//    POST: localhost:9000/newTodo?title=Go%20Walking
    @PostMapping("/newTodo")
    public ResponseEntity<?> newTodo(@RequestParam("title") String title){
        this.todoService.createNewTodo(title);
        return ResponseEntity.ok().body("Create Todo Successfully.");
    }

    @PutMapping("/completingTodo")
    public ResponseEntity<?> completingTodo(@RequestParam("id") Long id){
        return this.todoService.completingTodo(id);
    }

    @DeleteMapping("/deleteTodo")
    public ResponseEntity<?> deletingTodo(@RequestParam("id") Long id){
        return this.todoService.deletingTodo(id);
    }
}
