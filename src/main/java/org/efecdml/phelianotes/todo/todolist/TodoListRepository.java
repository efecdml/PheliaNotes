package org.efecdml.phelianotes.todo.todolist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    @Query(value = "SELECT * FROM todo_list t WHERE t.owner_id = :ownerId", nativeQuery = true)
    List<TodoList> findAllByOwner(Long ownerId);

    @Query(value = "SELECT * FROM todo_list t WHERE t.name = :name AND t.owner_id = :ownerId", nativeQuery = true)
    Optional<TodoList> findByNameAndOwner(String name, Long ownerId);

    @Query(value = "SELECT * FROM todo_list t WHERE t.name = :name AND t.owner_id = :ownerId AND NOT t.id = :id", nativeQuery = true)
    Optional<TodoList> findRepeatedNameByOwner(String name, Long ownerId, Long id);
}
