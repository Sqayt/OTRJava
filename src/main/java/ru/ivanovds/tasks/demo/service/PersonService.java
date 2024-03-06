package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.dto.PersonDto;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Person;
import ru.ivanovds.tasks.demo.handler.PersonException;
import ru.ivanovds.tasks.demo.repository.impl.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository repository;

    public boolean savePerson(PersonDto personDto) {
        Long directorId = personDto.getDirectorFullName().equals("") ?
                null : Long.parseLong(personDto.getDirectorFullName());

        if (isNotValidPersonDto(personDto) || (directorId != null && Objects.equals(directorId, personDto.getId()))) {
            return false;
        }

        Person person = new Person(
                null, personDto.getPost(), personDto.getName(),
                personDto.getSurName(), personDto.getMiddleName(),
                personDto.getBranchName(), directorId);

        return repository.insert(person);
    }

    public PersonDto getPersonById(Long id) throws Exception {
        Person person = repository.findById(id);
        PersonDto personDto = new PersonDto(person);

        personDto.setDirectorFullName(repository.findFullNameById(person.getFkDirectorPersonId()));
        personDto.setCountTask(repository.countTaskByPersonId(personDto.getId()));

        return personDto;
    }

    public List<PersonDto> getAllPerson() {
        List<Person> people = repository.findAll();
        List<PersonDto> peopleDto = new ArrayList<>(people.size());

        people.forEach(it -> {
            PersonDto personDto = new PersonDto(it);

            people.forEach(
                el -> {
                    try {
                        if (el.getId().equals(it.getFkDirectorPersonId())) {
                            if (el.getSurName().isEmpty() || el.getName().isEmpty() || el.getMiddleName().isEmpty()) {
                                throw new PersonException("Fatal exception in db 'people'");
                            }
                            personDto.setDirectorFullName(el.getSurName() + " " + el.getName() + " "
                                    + el.getMiddleName());
                        }
                        personDto.setCountTask(repository.countTaskByPersonId(el.getId()));
                    } catch (PersonException e) {
                        throw new RuntimeException(e);
                    }
                });

            peopleDto.add(personDto);
        });

        return peopleDto;
    }

    public boolean updatePersonById(Long id, PersonDto personDto){
        try {
            Long directorId = personDto.getDirectorFullName().equals("") ?
                    null : Long.parseLong(personDto.getDirectorFullName());

            Person personOld = repository.findById(id);
            personDto.setBranchName(personOld.getBranchName());

            if (isNotValidPersonDto(personDto) || Objects.equals(directorId, personDto.getId())) {
                return false;
            }

            Person person = new Person(
                    personDto.getId(), personDto.getPost(), personDto.getName(),
                    personDto.getSurName(), personDto.getMiddleName(),
                    personDto.getBranchName(), directorId);

            return repository.update(id, person);
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    public boolean delAllPerson() {
        return repository.deleteAll();
    }

    public boolean deletePersonById(Long id) {
        int countTask = Integer.parseInt(repository.countTaskByPersonId(id));
        int countPerson = Integer.parseInt(repository.countPersonByPersonId(id));

        if (countPerson == 0 && countTask == 0){
            return repository.delete(id);
        } else {
            return false;
        }
    }

    private boolean isNotValidPersonDto(PersonDto personDto) {
        return  personDto == null                 ||
                personDto.getPost() == null       || personDto.getPost().isEmpty()       ||
                personDto.getName() == null       || personDto.getName().isEmpty()       ||
                personDto.getSurName() == null    || personDto.getSurName().isEmpty()    ||
                personDto.getMiddleName() == null || personDto.getMiddleName().isEmpty() ||
                personDto.getBranchName() == null || personDto.getBranchName().isEmpty();
    }
}
