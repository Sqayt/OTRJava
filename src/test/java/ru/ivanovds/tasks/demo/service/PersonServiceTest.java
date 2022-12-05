package ru.ivanovds.tasks.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Person;

import java.util.List;

@SpringBootTest
@Slf4j
public class PersonServiceTest {

    @Autowired
    PersonService personService;

    @Test
    public void savePersonTest() {
        Person person = new Person(
                1L, "Начальник", "Юрий", "Мерзляков",
                "Владимирович", "Лукойл", null
        );
        personService.savePerson(person);

        List<Person> people = personService.getAllPerson();
        int size = people.size();

        Assertions.assertNotEquals(size, 0);
    }

    @Test
    public void savePersonWithoutIdTest() {
        Person person = new Person(
                null, "Шеф", "Владимир", "Сергеевич",
                "Семенюк", "Twitch", null
        );

        personService.savePerson(person);

        List<Person> people = personService.getAllPerson();
        int size = people.size();

        Assertions.assertEquals(size, 2);
    }

    @Test
    public void getPersonByIdTest() throws Exception {
        List<Person> people = personService.getAllPerson();
        int size = people.size();
        Long id = people.get(size - 1).getId();

        Person person = personService.getPersonById(id);

        Assertions.assertEquals(person.getName(), people.get(size - 1).getName());
    }

    @Test
    public void updatePersonByIdTest() {
        Person person = new Person(
                null, "Директор", "Максим", "Иванов",
                "Владимирович", "ОАО", 1L
        );
        List<Person> people = personService.getAllPerson();
        Long id = people.get(people.size() - 1).getId();

        personService.updatePersonById(id, person);

        List<Person> personList = personService.getAllPerson();

        Assertions.assertEquals(person.getName(), personList.get(personList.size() - 1).getName());
    }

    @Test
    public void deletePersonByIdTest() {
        List<Person> people = personService.getAllPerson();
        int size = people.size();
        Long id = people.get(size - 1).getId();

        personService.deletePersonById(id);

        List<Person> personList = personService.getAllPerson();
        int sizeNew = personList.size();

        Assertions.assertNotEquals(size, sizeNew);
    }

    @Test
    public void delAllPersonTest() {
        personService.delAllPerson();

        List<Person> people = personService.getAllPerson();

        Assertions.assertEquals(people.size(), 0);
    }
}
