package ru.ivanovds.tasks.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import ru.ivanovds.tasks.demo.entity.Tables;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Person;
import ru.ivanovds.tasks.demo.repository.CrudRepository;

import java.util.List;
import java.util.Objects;

import static org.jooq.impl.DSL.count;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PersonRepository implements CrudRepository<Person> {

    private final DSLContext context;

    @Override
    public boolean insert(Person person) {
        try {
            context
                    .insertInto(Tables.PERSON)
                    .set(context.newRecord(Tables.PERSON, person))
                    .returning()
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error into person: " + person.toString()));

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    @Override
    public boolean update(Long id, Person person) {
        try {
            context
                    .update(Tables.PERSON)
                    .set(context.newRecord(Tables.PERSON, person))
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

    @Override
    public Person findById(Long id) throws Exception {
        try {
            return Objects.requireNonNull(context
                            .selectFrom(Tables.PERSON)
                            .where(Tables.PERSON.ID.eq(id))
                            .fetchAny())
                    .into(Person.class);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new Exception();
        }
    }

    @Override
    public List<Person> findAll() {
        return context
                .selectFrom(Tables.PERSON)
                .fetchInto(Person.class);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            context
                    .deleteFrom(Tables.PERSON)
                    .where(Tables.PERSON.ID.eq(id))
                    .execute();

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    @Override
    public Boolean deleteAll() {
        try {
            context
                    .deleteFrom(Tables.PERSON)
                    .execute();

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    //TODO подумать
    public String findFullNameById(Long id) throws Exception {
        try {
            var result = context
                    .select(Tables.PERSON.SUR_NAME, Tables.PERSON.NAME, Tables.PERSON.MIDDLE_NAME)
                    .from(Tables.PERSON)
                    .where(Tables.PERSON.ID.eq(id))
                    .fetchAny();

            StringBuilder word = new StringBuilder();
            String words = "";

            if (result != null) {
                words = word.append(result.getValue(0)).append(" ")
                        .append(result.getValue(1)).append(" ")
                        .append(result.getValue(2)).append(" ")
                        .toString();

            }

            if (words.isEmpty()) {
                return "";
            }

            return words;
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new Exception("Ошибка в сервисе");
        }
    }
}
