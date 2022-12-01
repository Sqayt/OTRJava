package ru.ivanovds.tasks.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "post", nullable = false)
    private String post;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sur_name", nullable = false)
    private String surName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @Column(name = "director_full_name")
    private String directorFullName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "person", orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        this.tasks.add(task);
        task.setPerson(this);
    }

    public void delTask(Task task) {
        this.tasks.remove(task);
        task.setPerson(null);
    }

    public Person(String post, String name, String surName, String middleName, String branchName, String directorFullName, List<Task> tasks) {
        if (!isEmpty(post)) this.post = post;
        if (!isEmpty(name)) this.name = name;
        if (!isEmpty(surName)) this.surName = surName;
        if (!isEmpty(middleName)) this.middleName = middleName;
        if (!isEmpty(branchName)) this.branchName = branchName;
        this.directorFullName = directorFullName;
        this.tasks = tasks;
    }

    public Person(String post, String name, String surName, String middleName, String branchName, List<Task> tasks) {
        if (!isEmpty(post)) this.post = post;
        if (!isEmpty(name)) this.name = name;
        if (!isEmpty(surName)) this.surName = surName;
        if (!isEmpty(middleName)) this.middleName = middleName;
        if (!isEmpty(branchName)) this.branchName = branchName;
        this.tasks = tasks;
    }

    public Person(String post, String name, String surName, String middleName, String branchName, String directorFullName) {
        if (!isEmpty(post)) this.post = post;
        if (!isEmpty(name)) this.name = name;
        if (!isEmpty(surName)) this.surName = surName;
        if (!isEmpty(middleName)) this.middleName = middleName;
        if (!isEmpty(branchName)) this.branchName = branchName;
        this.directorFullName = directorFullName;
    }

    public Person(String post, String name, String surName, String middleName, String branchName) {
        if (!isEmpty(post)) this.post = post;
        if (!isEmpty(name)) this.name = name;
        if (!isEmpty(surName)) this.surName = surName;
        if (!isEmpty(middleName)) this.middleName = middleName;
        if (!isEmpty(branchName)) this.branchName = branchName;
    }

    public Person(Person person) {
        if (!isEmpty(person.getPost())) this.post = person.getPost();
        if (!isEmpty(person.getName())) this.name = person.getName();
        if (!isEmpty(person.getSurName())) this.surName = person.getSurName();
        if (!isEmpty(person.getMiddleName())) this.middleName = person.getMiddleName();
        if (!isEmpty(person.getBranchName())) this.branchName = person.getBranchName();
        this.directorFullName = person.getDirectorFullName();
        this.tasks = person.getTasks();
    }

    public void setTasks(List<Task> tasks) {
        if (tasks != null) {
            tasks.forEach(it ->
                it.setPerson(this));
        }
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(post, person.post) && Objects.equals(name, person.name) && Objects.equals(surName, person.surName) && Objects.equals(middleName, person.middleName) && Objects.equals(branchName, person.branchName) && Objects.equals(directorFullName, person.directorFullName) && Objects.equals(tasks, person.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, post, name, surName, middleName, branchName, directorFullName, tasks);
    }

    private boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
