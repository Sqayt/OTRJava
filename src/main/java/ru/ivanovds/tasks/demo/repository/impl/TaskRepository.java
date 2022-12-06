package ru.ivanovds.tasks.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import ru.ivanovds.tasks.demo.entity.Tables;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Task;
import ru.ivanovds.tasks.demo.repository.CrudRepository;

import java.util.List;
import java.util.Objects;

import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TaskRepository implements CrudRepository<Task> {

    private final DSLContext context;

    @Override
    public boolean insert(Task task) {
        try {
            context
                    .insertInto(Tables.TASK)
                    .set(context.newRecord(Tables.TASK, task))
                    .returning()
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error insert into" + task.toString()));

            return true;
        } catch (DataAccessException e) {
            log.error(e.getMessage());

            return false;
        }
    }

    @Override
    public boolean update(Long id, Task task) {
        try {
            context
                    .update(Tables.TASK)
                    .set(context.newRecord(Tables.TASK, task))
                    .where(Tables.TASK.ID.eq(id))
                    .returning()
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error in update by id: "
                            + id + ", task: " + task));

            return true;
        } catch (DataAccessException e) {
            log.error(e.getMessage());

            return false;
        }
    }

    @Override
    public Task findById(Long id) throws Exception {
        try {
            return Objects.requireNonNull(context
                            .selectFrom(Tables.TASK)
                            .where(Tables.TASK.ID.eq(id))
                            .fetchAny())
                    .into(Task.class);

        } catch (Exception e) {
            log.error(e.getMessage());

            throw new Exception("Ошибка в сервисе");
        }
    }

    @Override
    public List<Task> findAll() {
        return context
                .selectFrom(Tables.TASK)
                .orderBy(Tables.TASK.PRIORITY.desc())
                .fetchInto(Task.class);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            context
                    .deleteFrom(Tables.TASK)
                    .where(Tables.TASK.ID.eq(id))
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
                    .deleteFrom(Tables.TASK)
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

    public Integer getMaxPriority() {
        return Objects.requireNonNull(context
                .select(max(Tables.TASK.PRIORITY))
                .from(Tables.TASK)
                .fetchAny()
        ).into(Integer.class);
    }

    public Integer getMinPriority() {
        return Objects.requireNonNull(context
                .select(min(Tables.TASK.PRIORITY))
                .from(Tables.TASK)
                .fetchAny()
        ).into(Integer.class);
    }
}
