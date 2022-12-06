package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.dto.PersonDto;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Person;
import ru.ivanovds.tasks.demo.repository.impl.PersonRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository repository;

    @Transactional
    public boolean savePerson(PersonDto personDto) {
        Long directorId = personDto.getDirectorFullName().equals("") ?
                null : Long.parseLong(personDto.getDirectorFullName());

        if (isNotValidPersonDto(personDto) || Objects.equals(directorId, personDto.getId())) {
            return false;
        }

        Person person = new Person(
                null, personDto.getPost(), personDto.getName(),
                personDto.getSurName(), personDto.getMiddleName(),
                personDto.getBranchName(), directorId);

        return repository.insert(person);
    }

    @Transactional
    public PersonDto getPersonById(Long id) throws Exception {
        Person person = repository.findById(id);
        PersonDto personDto = new PersonDto(person);

        personDto.setDirectorFullName(repository.findFullNameById(person.getFkDirectorPersonId()));
        personDto.setCountTask(repository.countTaskByPersonId(personDto.getId()));

        return personDto;
    }

    @Transactional
    public List<PersonDto> getAllPerson() {
        List<Person> people = repository.findAll();
        List<PersonDto> peopleDto = new ArrayList<>(people.size());

        people.forEach(it -> {
            try {
                PersonDto personDto = new PersonDto(it);

                personDto.setDirectorFullName(repository.findFullNameById(it.getFkDirectorPersonId()));
                personDto.setCountTask(repository.countTaskByPersonId(it.getId()));

                peopleDto.add(personDto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return peopleDto;
    }

    @Transactional
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

    @Transactional
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
        return personDto.getPost().isEmpty() || personDto.getPost().equals("") || personDto.getPost() == null ||
                personDto.getName().isEmpty() || personDto.getName().equals("") || personDto.getName() == null ||
                personDto.getSurName().isEmpty() || personDto.getSurName().equals("") || personDto.getSurName() == null ||
                personDto.getMiddleName().isEmpty() || personDto.getMiddleName().equals("") || personDto.getMiddleName() == null ||
                personDto.getBranchName().isEmpty() || personDto.getBranchName().equals("") || personDto.getBranchName() == null;
    }
}
