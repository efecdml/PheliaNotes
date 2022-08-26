package org.efecdml.phelianotes.todo.todolist;

import lombok.AllArgsConstructor;
import org.efecdml.phelianotes.appuser.AppUser;
import org.efecdml.phelianotes.appuser.AppUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final AppUserService appUserService;

    public List<TodoList> getAllTodoLists() {
        return todoListRepository.findAll();
    }

    public List<TodoList> getAllTodoListsByOwner(Long ownerId) {
        return todoListRepository.findAllByOwner(ownerId);
    }

    public Optional<TodoList> getTodoListByOwner(String name, Long ownerId) {
        return todoListRepository.findByNameAndOwner(name, ownerId);
    }

    public Optional<TodoList> getTodoList(Long id) {
        return todoListRepository.findById(id);
    }

    public void addTodoList(TodoList todoList, Long ownerId) {
        Optional<TodoList> todoListOptional = todoListRepository.findByNameAndOwner(todoList.getName(), ownerId);

        if (todoListOptional.isPresent()) {
            throw new IllegalStateException("Todo list with name " + todoList.getName() + " is already exists..");
        }

        AppUser appUser = appUserService.getUser(ownerId)
                .orElseThrow(() -> new IllegalStateException("User couldn't found.."));
        todoList.setOwner(appUser);
        todoList.setDateCreated(LocalDateTime.now());

        todoListRepository.save(todoList);
    }

    public void updateTodoList(
            Long id,
            String name,
            Long ownerId
    ) {
        TodoList todoList = todoListRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Todo list with ID " + id + " couldn't found.."));

        Optional<TodoList> todoListOptional = todoListRepository.findRepeatedNameByOwner(
                name,
                ownerId,
                id
        );

        if (isOwner(id, ownerId)) {
            if (!todoListOptional.isPresent()) {
                if (name != null && name.length() > 0) {
                    todoList.setName(name);
                    todoList.setDateUpdated(LocalDateTime.now());
                }
            } else {
                throw new IllegalStateException("This name is already exists..");
            }
        }

        todoListRepository.save(todoList);
    }

    public void deleteTodoList(Long id, Long ownerId) {
        if (isOwner(id, ownerId)) {
            todoListRepository.deleteById(id);
        }
    }

    private boolean isOwner(Long todoListId, Long ownerId) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new IllegalStateException("Todo list with ID " + todoListId + " couldn't found.."));

        if (Objects.equals(ownerId, todoList.getOwner().getId())) {
            return true;
        }

        throw new IllegalStateException("Ownership with todo list didn't match..");
    }
}
