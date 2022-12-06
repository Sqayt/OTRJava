package ru.ivanovds.tasks.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivanovds.tasks.demo.dto.PersonDto;
import ru.ivanovds.tasks.demo.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;

    @PostMapping()
    public ResponseEntity<HttpStatus> savePerson(@RequestBody PersonDto person) {
        if (personService.savePerson(person)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllPerson() {
        try {
            List<PersonDto> people = personService.getAllPerson();

            if (people != null) {
                return new ResponseEntity<>(people, HttpStatus.OK);
            } else  {
                throw new Exception("Список is null");
            }
        } catch (Exception e) {
            log.error(e.getMessage());

            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable Long id) {
        try {
            PersonDto person = personService.getPersonById(id);

            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePersonById(@PathVariable Long id) {
        if (personService.deletePersonById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updatePersonById(@PathVariable Long id,
                                                       @RequestBody PersonDto person) {
        if (personService.updatePersonById(id, person)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
