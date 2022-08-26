package org.efecdml.phelianotes.todo.todolist;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/todolists")
public class TodoListController {
    private final TodoListService todoListService;

    @GetMapping
    public List<TodoList> getAllTodoLists() {
        return todoListService.getAllTodoLists();
    }

    @GetMapping("/{ownerId}")
    public List<TodoList> getAllTodoListsByOwner(@PathVariable Long ownerId) {
        return todoListService.getAllTodoListsByOwner(ownerId);
    }

    @GetMapping("/{ownerId}/{name}")
    public Optional<TodoList> getTodoListByOwner(
            @PathVariable("ownerId") Long ownerId,
            @PathVariable("name") String name
    ) {
        return todoListService.getTodoListByOwner(name, ownerId);
    }

    @PostMapping("/{ownerId}")
    public void addTodoList(
            @PathVariable("ownerId") Long ownerId,
            @RequestBody TodoList todoList
    ) {
        todoListService.addTodoList(todoList, ownerId);
    }

    @PutMapping("/{ownerId}/{id}")
    public void updateTodoList(
            @PathVariable("ownerId") Long ownerId,
            @PathVariable("id") Long id,
            @RequestParam(required = false) String name
    ) {
        todoListService.updateTodoList(
                id,
                name,
                id
        );
    }

    @DeleteMapping("/{ownerId}/{id}")
    public void deleteTodoList(
            @PathVariable("ownerId") Long ownerId,
            @PathVariable("id") Long id
    ) {
        todoListService.deleteTodoList(id, ownerId);
    }
}
