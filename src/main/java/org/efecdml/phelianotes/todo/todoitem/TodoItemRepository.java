package org.efecdml.phelianotes.todo.todoitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    @Query(value = "SELECT * FROM todo_item t WHERE t.list_id = :listId", nativeQuery = true)
    List<TodoItem> findAllByList(Long listId);

    @Query(value = "SELECT * FROM todo_item t WHERE t.name = :name AND t.list_id = :listId", nativeQuery = true)
    Optional<TodoItem> findByNameAndList(String name, Long listId);

    @Query(value = "SELECT * FROM todo_item t WHERE t.name = :name AND t.list_id = :listId AND NOT t.id = :id", nativeQuery = true)
    Optional<TodoItem> findRepeatedNameByList(String name, Long listId, Long id);
}
