package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.entity.Person;
import ru.ivanovds.tasks.demo.entity.Task;
import ru.ivanovds.tasks.demo.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    public boolean savePerson(Person person) {
        try {
            Person personNew = new Person(person);
            personRepository.save(personNew);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    public Person getPersonById(Long id) {
        try {
            return personRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            log.error(e.getMessage());

            return new Person();
        }
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

    @Transactional
    public boolean addTaskByPerson(Long id, Task task) {
        try {
            Person person = personRepository.findById(id).orElseThrow();
            Task taskNew = new Task(task);

            person.addTask(taskNew);
            personRepository.save(person);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage() + " It is error");

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

            if (!personOld.getDirectorFullName().equals(person.getDirectorFullName())) {
                personOld.setDirectorFullName(person.getDirectorFullName());
            } else {
                throw new Exception();
            }

            personRepository.save(personOld);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    public boolean delTaskByPerson(Person person, Task task) {
        try {
            person.delTask(task);

            personRepository.save(person);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    public boolean delAllPerson() {
        try {
            personRepository.deleteAll();

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    public boolean deletePersonById(Long id) {
        try {
            Person person = personRepository.findById(id).orElseThrow();
            if (person.getTasks().size() == 0) {
                personRepository.delete(person);
            } else {
                throw new Exception();
            }

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }
}
