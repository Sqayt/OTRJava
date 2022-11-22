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

    public boolean savePerson(Person person) {
        try {
            personRepository.save(person);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public List<Task> getAllTaskByPerson(Person person) {
        try {
            return person.getTasks();
        } catch (Exception e) {
            log.error(e.getMessage());

            return new ArrayList<>();
        }
    }

    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    public boolean addTaskByPerson(Person person, Task task) {
        try {
            person.addTask(task);
            personRepository.save(person);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

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
            personOld.setDirectorFullName(person.getDirectorFullName());

            personRepository.save(personOld);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delTaskByPerson(Person person, Task task) {
        try {
            person.delTask(task);

            personRepository.save(person);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delAllPerson() {
        try {
            personRepository.deleteAll();

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
}
