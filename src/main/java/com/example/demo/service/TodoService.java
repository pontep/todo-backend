package com.example.demo.service;

import com.example.demo.entity.Todo;
import com.example.demo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> getAllTodos(){
        log.info("getAllTodos completed");
        return this.todoRepository.findAll();
    }

    public void createNewTodo(String title){
        log.info("createNewTodo completed");
        Todo todo = new Todo();
        todo.setTitle(title);
        this.todoRepository.save(todo);
    }

    public ResponseEntity<?> completingTodo(Long id){
        log.info("completingTodo completed");
        Optional<Todo> todo = this.todoRepository.findById(id);
        if(todo.isPresent()){
            todo.get().setCompleted(!todo.get().isCompleted());
            this.todoRepository.save(todo.get());
            return ResponseEntity.ok().body("CompletingTodo Sucessfully.");
        }else{
            return ResponseEntity.badRequest().body("Todo Id is not exist!");
        }
    }

    public ResponseEntity<?> deletingTodo(Long id){
        try{
            Optional<Todo> todo = this.todoRepository.findById(id);
            if(todo.isPresent()){
                this.todoRepository.deleteById(id);
                return ResponseEntity.ok().body("Deleted Todo");
            }else{
                return ResponseEntity.badRequest().body("Todo Id is not exist!");
            }

        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Unknown ERROR");
        }
    }
}
