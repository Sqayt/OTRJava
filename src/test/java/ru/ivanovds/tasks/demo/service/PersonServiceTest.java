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
        Person person = personService.getPersonById(1L);
        Person personOld = new Person("Начальник", "Михаил", "Задорнов", "Сергеевич", "ОАО");

        Assertions.assertEquals(person.getName(), personOld.getName());

    }

    @Test
    public void savePersonTest() {
        Person person = new Person("Начальник", "Михаил", "Быков", "Сергеевич", "ОАО", "Задорнов Михаил Сергеевич");
        personService.savePerson(person);
        List<Person> people = personService.getAllPerson();
        Person personNew = people.get(people.size() - 1);

        Assertions.assertEquals(person.getName(), personNew.getName());
    }

    @Test
    public void notSavePersonTest() {
        Person person = new Person("Начальник", "", "Быков", "Сергеевич", "ОАО", "Задорнов Михаил Сергеевич");
        personService.savePerson(person);
        List<Person> people = personService.getAllPerson();
        Person personNew = people.get(people.size() - 1);

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
        Person person = personService.getPersonById(1L);

        Assertions.assertEquals(person.getName(), personNew.getName());
    }

    @Test
    public void getAllTaskByPersonTest() {
        Person person = personService.getPersonById(1L);
        List<Task> tasks = personService.getAllTaskByPerson(person);

        Assertions.assertEquals(tasks.size(), 2);
    }

    @Test
    public void addTaskByPersonTest() {
        Task task = new Task(18, "Это Тест");
        personService.addTaskByPerson(1L, task);
        Person personNew = personService.getPersonById(1L);
        List<Task> tasks = personNew.getTasks();

        Assertions.assertNotEquals(tasks.size(), 2);
    }

    @Test
    public void delTaskByPersonTest() {
        Person person = personService.getPersonById(10L);
        List<Task> tasks = person.getTasks();
        Task task = person.getTasks().get(0);
        personService.delTaskByPerson(person, task);

        Assertions.assertEquals(tasks.size(), 1);
    }

    @Test
    public void delAllPersons() {
        personService.delAllPerson();
        List<Person> people = personService.getAllPerson();

        Assertions.assertEquals(people.size(), 0);
    }
}
