package ru.ivanovds.tasks.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ivanovds.tasks.demo.entity.Person;
import ru.ivanovds.tasks.demo.entity.Task;

import java.util.List;

@SpringBootTest
@Slf4j
public class PersonServiceTest {

    @Autowired
    PersonService personService;

    @Autowired
    TaskService taskService;

    @Test
    public void getAllPersonTest() {
        List<Person> people = personService.getAllPerson();

        Assertions.assertNotEquals(people.size(), 0);
    }

    @Test
    public void getPersonByIdTest() {
        Person person = personService.getPersonById(1L).orElse(new Person());
        Person personOld = new Person("Начальник", "Михаил", "Задорнов", "Сергеевич", "ОАО");

        Assertions.assertEquals(person.getName(), personOld.getName());

    }

    @Test
    public void savePersonTest() {
        Person person = new Person("Начальник", "Михаил", "Иванов", "Сергеевич", "ОАО");
        personService.savePerson(person);
        Person personNew = personService.getPersonById(4L).orElse(new Person());

        Assertions.assertEquals(person.getName(), personNew.getName());
    }

    @Test
    public void deletePersonByIdTest() {
        List<Person> people = personService.getAllPerson();
        personService.deletePersonById(1L);
        List<Person> peopleNew = personService.getAllPerson();

        Assertions.assertNotEquals(people.size(), peopleNew.size());
    }

    @Test
    public void updatePersonByIdTest() {
        Person personNew = new Person("Начальник", "Сергей", "Задорнов", "Сергеевич", "ОАО");
        personService.updatePersonById(1L, personNew);
        Person person = personService.getPersonById(1L).orElse(new Person());

        Assertions.assertEquals(person.getName(), personNew.getName());
    }

    @Test
    public void saveTaskByPersonTest() {
        Task task = new Task(10, "Починить самолет");
        Person person = personService.getPersonById(1L).orElse(new Person());
        personService.saveTaskByPerson(person, task);

        Task taskNew = taskService.getTaskById(4L).orElse(new Task());


        Assertions.assertEquals(task.getDescription(), taskNew.getDescription());
    }

    @Test
    public void deleteTaskByPersonTest() {
        Task task = taskService.getTaskById(1L).orElse(new Task());
        Person person = personService.getPersonById(1L).orElse(new Person());
        List<Task> tasksOld = person.getTasks();
        taskService.deleteTaskById(task.getId());
        personService.deleteTaskByPerson(person, task);

        Person personNew = personService.getPersonById(1L).orElse(new Person());
        List<Task> tasks = personNew.getTasks();

        Assertions.assertNotEquals(tasksOld.size(), tasks.size());
    }

    @Test
    public void getAllTaskByPersonTest() {
        Person person = personService.getPersonById(1L).orElse(new Person());
        List<Task> tasks = personService.getAllTaskByPerson(person);

        Assertions.assertEquals(tasks.size(), 2);
    }
}
