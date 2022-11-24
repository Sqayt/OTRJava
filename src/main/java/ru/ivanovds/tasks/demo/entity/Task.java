package ru.ivanovds.tasks.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "task")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "priority", nullable = false)
    private int priority;

    @Column(name = "description", nullable = false)
    private String description;

    @JsonBackReference
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "person_id")
    private Person person;

    public Task(int priority, String description) {
        this.priority = priority;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return priority == task.priority && Objects.equals(id, task.id) && Objects.equals(description, task.description) && Objects.equals(person, task.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priority, description, person);
    }
}
