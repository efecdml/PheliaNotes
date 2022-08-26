package org.efecdml.phelianotes.todo.todolist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.efecdml.phelianotes.appuser.AppUser;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class TodoList {
    @Id
    @SequenceGenerator(
            name = "todoList_sequence",
            sequenceName = "todoList_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "todoList_sequence"
    )
    private Long id;
    private String name;
    @JoinColumn(name = "owner_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser owner;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
