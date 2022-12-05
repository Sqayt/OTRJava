package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Person;
import ru.ivanovds.tasks.demo.repository.impl.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository repository;

    @Transactional
    public boolean savePerson(Person person) {
        return repository.insert(person);
    }

    @Transactional
    public Person getPersonById(Long id) throws Exception {
        return repository.findById(id);
    }

    public List<Person> getAllPerson() {
        return repository.findAll();
    }

    @Transactional
    public boolean updatePersonById(Long id, Person person) {
        return repository.update(id, person);
    }

    public boolean delAllPerson() {
        return repository.deleteAll();
    }

    @Transactional
    public boolean deletePersonById(Long id) {
        return repository.delete(id);
    }
}
