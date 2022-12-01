package ru.ivanovds.tasks.demo.dto;

import lombok.Data;
import ru.ivanovds.tasks.demo.entity.Task;

@Data
public class TaskDto {
    private Long id;
    private String description;
    private String fullNamePerson;
    private int priority;

    private Long personId;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.fullNamePerson = task.getPerson().getSurName() + " " + task.getPerson().getName() + " " +
                task.getPerson().getMiddleName();
    }

    public TaskDto() {
    }

    public TaskDto(int priority, String description) {
        this.description = description;
        this.priority = priority;
    }

    public TaskDto(int priority, String description, Long personId) {
        this.description = description;
        this.priority = priority;
        this.personId = personId;
    }

}
