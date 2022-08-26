package org.efecdml.phelianotes.todo.todoitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.efecdml.phelianotes.todo.todolist.TodoList;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class TodoItem {
    @Id
    @SequenceGenerator(
            name = "todoItem_sequence",
            sequenceName = "todoItem_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "todoItem_sequence"
    )
    private Long id;
    private String name;
    @JoinColumn(name = "list_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TodoList todoList;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
