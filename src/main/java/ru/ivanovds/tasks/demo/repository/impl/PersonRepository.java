package ru.ivanovds.tasks.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import ru.ivanovds.tasks.demo.entity.Tables;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Person;
import ru.ivanovds.tasks.demo.repository.CrudRepository;

import java.util.List;
import java.util.Objects;

import static org.jooq.impl.DSL.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PersonRepository implements CrudRepository<Person> {

    private final DSLContext context;

    @Override
    public boolean insert(Person person) {
        context
                .insertInto(Tables.PERSON)
                .set(context.newRecord(Tables.PERSON, person))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error into person: " + person.toString()));

        return true;
    }

    @Override
    public boolean update(Long id, Person person) {
            context
                    .update(Tables.PERSON)
                    .set(context.newRecord(Tables.PERSON, person))
                    .where(Tables.PERSON.ID.eq(id))
                    .returning()
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error update entity by id:" + id))
                    .into(Person.class);

            return true;
    }

    @Override
    public Person findById(Long id) {
        return context
                        .selectFrom(Tables.PERSON)
                        .where(Tables.PERSON.ID.eq(id))
                        .fetchAny()
                        .into(Person.class);
    }

    @Override
    public List<Person> findAll() {
        return context
                        .selectFrom(Tables.PERSON)
                        .fetchInto(Person.class);
    }

    @Override
    public Boolean delete(Long id) {
        context
                .deleteFrom(Tables.PERSON)
                .where(Tables.PERSON.ID.eq(id))
                .execute();

        return true;
    }

    @Override
    public Boolean deleteAll() {
        context
                .deleteFrom(Tables.PERSON)
                .execute();

        return true;
    }

    //TODO подумать
    public String findFullNameById(Long id) {
        Result<Record1<String>> result = context
                .select(concat(Tables.PERSON.SUR_NAME, val(" "), Tables.PERSON.NAME, val(" "), Tables.PERSON.MIDDLE_NAME))
                .from(Tables.PERSON)
                .where(Tables.PERSON.ID.eq(id))
                .fetch();

        return result.format();
    }

    public String countTaskByPersonId(Long id) {
        return context
                        .select(count())
                        .from(Tables.TASK)
                        .where(Tables.TASK.PERSON_ID.eq(id))
                        .fetchAny()
                        .into(String.class);
    }

    public String countPersonByPersonId(Long id) {
        return context
                        .select(count())
                        .from(Tables.PERSON)
                        .where(Tables.PERSON.FK_DIRECTOR_PERSON_ID.eq(id))
                        .fetchAny()
                        .into(String.class);
    }
}
