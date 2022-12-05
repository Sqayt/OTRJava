package ru.ivanovds.tasks.demo.dto;

import lombok.Data;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Task;

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
        this.personId = task.getPersonId();
        this.fullNamePerson = String.valueOf(task.getPersonId());
    }

    public TaskDto() {
    }

    public TaskDto(int priority, String description, Long personId) {
        this.description = description;
        this.priority = priority;
        this.personId = personId;
    }

}
