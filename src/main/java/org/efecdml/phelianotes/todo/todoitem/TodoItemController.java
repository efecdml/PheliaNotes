package org.efecdml.phelianotes.todo.todoitem;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/todoitems")
public class TodoItemController {
    private final TodoItemService todoItemService;

    @GetMapping
    public List<TodoItem> getAllTodoItems() {
        return todoItemService.getAllTodoItems();
    }

    @GetMapping("/{listId}")
    public List<TodoItem> getAllTodoItemsByList(@PathVariable Long listId) {
        return todoItemService.getAllTodoItemsByList(listId);
    }

    @PostMapping("/{listId}")
    public void addTodoItem(
            @PathVariable("listId") Long listId,
            @RequestBody TodoItem todoItem
    ) {
        todoItemService.addTodoItem(todoItem, listId);
    }

    @PutMapping("/{listId}/{id}")
    public void updateTodoItem(
            @PathVariable("listId") Long listId,
            @PathVariable("id") Long id,
            @RequestParam(required = false) String name
    ) {
        todoItemService.updateTodoItem(
                id,
                name,
                listId
        );
    }

    @DeleteMapping("/{listId}/{id}")
    public void deleteTodoItem(
            @PathVariable("listId") Long listId,
            @PathVariable("id") Long id
    ) {
        todoItemService.deleteTodoItem(id, listId);
    }
}
