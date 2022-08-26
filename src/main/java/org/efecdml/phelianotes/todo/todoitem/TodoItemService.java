package org.efecdml.phelianotes.todo.todoitem;

import lombok.AllArgsConstructor;
import org.efecdml.phelianotes.todo.todolist.TodoList;
import org.efecdml.phelianotes.todo.todolist.TodoListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TodoItemService {
    private final TodoItemRepository todoItemRepository;
    private final TodoListService todoListService;

    public List<TodoItem> getAllTodoItems() {
        return todoItemRepository.findAll();
    }

    public List<TodoItem> getAllTodoItemsByList(Long listId) {
        return todoItemRepository.findAllByList(listId);
    }

    public void addTodoItem(TodoItem todoItem, Long listId) {
        Optional<TodoItem> todoItemOptional = todoItemRepository.findByNameAndList(todoItem.getName(), listId);

        if (todoItemOptional.isPresent()) {
            throw new IllegalStateException("Todo item with name " + todoItem.getName() + " is already exists..");
        }

        TodoList todoList = todoListService.getTodoList(listId)
                .orElseThrow(() -> new IllegalStateException("Todo list couldn't found.."));
        todoItem.setTodoList(todoList);
        todoItem.setDateCreated(LocalDateTime.now());

        todoItemRepository.save(todoItem);
    }

    public void updateTodoItem(
            Long id,
            String name,
            Long listId
    ) {
        TodoItem todoItem = todoItemRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Todo item with ID " + id + " couldn't found.."));

        Optional<TodoItem> todoItemOptional = todoItemRepository.findRepeatedNameByList(
                name,
                listId,
                id
        );

        if (isOwner(id, listId)) {
            if (!todoItemOptional.isPresent()) {
                if (name != null && name.length() > 0) {
                    todoItem.setName(name);
                    todoItem.setDateUpdated(LocalDateTime.now());
                }
            } else {
                throw new IllegalStateException("This name is already exists..");
            }
        }

        todoItemRepository.save(todoItem);
    }

    public void deleteTodoItem(Long id, Long listId) {
        if (isOwner(id, listId)) {
            todoItemRepository.deleteById(id);
        }
    }

    private boolean isOwner(Long itemId, Long listId) {
        TodoItem todoItem = todoItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalStateException("Todo item with ID " + itemId + " couldn't found.."));

        if (Objects.equals(listId, todoItem.getTodoList().getId())) {
            return true;
        }

        throw new IllegalStateException("Ownership with todo item didn't match..");
    }
}
