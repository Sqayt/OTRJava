package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.entity.Person;
import ru.ivanovds.tasks.demo.entity.Task;
import ru.ivanovds.tasks.demo.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public List<Task> getAllTaskByPerson(Person person) {
        try {
            Person personNew = personRepository.findById(person.getId()).orElse(new Person());

            return personNew.getTasks();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public boolean savePerson(Person person) {
        try {
            personRepository.save(person);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletePersonById(Long id) {
        try {
            Person person = personRepository.findById(id).orElseThrow();
            personRepository.delete(person);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updatePersonById(Long id, Person person) {
        try {
            Person personOld = personRepository.findById(id).orElseThrow();

            personOld.setBranchName(person.getBranchName());
            personOld.setTasks(person.getTasks());
            personOld.setName(person.getName());
            personOld.setSurName(person.getSurName());
            personOld.setMiddleName(person.getMiddleName());
            personOld.setPost(person.getPost());
            personOld.setDirectorId(person.getDirectorId());

            personRepository.save(personOld);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveTaskByPerson(Person person, Task task) {
        try {
            person.setTasks(List.of(task));
            personRepository.save(person);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //TODO не работает
    public boolean deleteTaskByPerson(Person person, Task task) {
        try {
            List<Task> tasks = person.getTasks();
            tasks.remove(task);
            person.setTasks(tasks);

            personRepository.save(person);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteAllPerson() {
        try {
            personRepository.deleteAll();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
