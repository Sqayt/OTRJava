package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Person;
import ru.ivanovds.tasks.demo.entity.Tables;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {
    private final DSLContext dslContext;

    @Transactional
    public boolean savePerson(Person person) {
        try {
            dslContext
                    .insertInto(Tables.PERSON)
                    .set(dslContext.newRecord(Tables.PERSON, person))
                    .returning()
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error into person: " + person.toString()));

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    @Transactional
    public Person getPersonById(Long id) throws Exception {
        try {
            return Objects.requireNonNull(dslContext
                            .selectFrom(Tables.PERSON)
                            .where(Tables.PERSON.ID.eq(id))
                            .fetchAny())
                    .into(Person.class);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new Exception();
        }
    }

    public List<Person> getAllPerson() {
        return dslContext
                .selectFrom(Tables.PERSON)
                .fetchInto(Person.class);
    }

    @Transactional
    public boolean updatePersonById(Long id, Person person) {
        try {
            dslContext
                    .update(Tables.PERSON)
                    .set(dslContext.newRecord(Tables.PERSON, person))
                    .where(Tables.PERSON.ID.eq(id))
                    .returning()
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error update entity by id:" + id))
                    .into(Person.class);

            return true;
        } catch (DataAccessException e) {
            log.error(e.getMessage());

            return false;
        }
    }

    public boolean delAllPerson() {
        try {
            dslContext
                    .deleteFrom(Tables.PERSON)
                    .execute();

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    @Transactional
    public boolean deletePersonById(Long id) {
        try {
            dslContext
                    .deleteFrom(Tables.PERSON)
                    .where(Tables.PERSON.ID.eq(id))
                    .execute();

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }
}
