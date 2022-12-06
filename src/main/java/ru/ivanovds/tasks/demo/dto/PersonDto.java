package ru.ivanovds.tasks.demo.dto;

import lombok.Data;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Person;

@Data
public class PersonDto {

    private Long id;
    private String name;
    private String middleName;
    private String surName;
    private String post;
    private String directorFullName;
    private String branchName;
    private String countTask;

    public PersonDto(Person person) {
        this.id = person.getId();
        this.post = person.getPost();
        this.name = person.getName();
        this.middleName = person.getMiddleName();
        this.surName = person.getSurName();
        this.branchName = person.getBranchName();
    }

    public PersonDto(Long id, String name, String middleName,
                     String surName, String post, String branchName,
                     String countTask) {
        this.id = id;
        this.name = name;
        this.middleName = middleName;
        this.surName = surName;
        this.post = post;
        this.branchName = branchName;
        this.countTask = countTask;
    }

    public PersonDto() {
    }
}
