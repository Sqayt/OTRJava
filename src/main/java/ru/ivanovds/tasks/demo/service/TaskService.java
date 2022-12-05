package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.dto.TaskDto;
import ru.ivanovds.tasks.demo.entity.Tables;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Task;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final DSLContext dslContext;

    public TaskDto getTaskById(Long id) throws Exception {
        try {
            Task task = Objects.requireNonNull(dslContext
                            .selectFrom(Tables.TASK)
                            .where(Tables.TASK.ID.eq(id))
                            .fetchAny())
                    .into(Task.class);

            return new TaskDto(task);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new Exception("Ошибка в сервисе");
        }
    }

    @Transactional
    public boolean saveTask(TaskDto taskDto) {
        try {
            Task task = new Task(null, taskDto.getPriority(), taskDto.getDescription(), taskDto.getPersonId());

            dslContext
                    .insertInto(Tables.TASK)
                    .set(dslContext.newRecord(Tables.TASK, task))
                    .returning()
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error insert into" + task.toString()));

            return true;
        } catch (DataAccessException e) {
            log.error(e.getMessage());

            return false;
        }
    }

    public List<TaskDto> getAllTask() {
        return dslContext
                .selectFrom(Tables.TASK)
                .orderBy(Tables.TASK.PRIORITY.desc())
                .fetchInto(TaskDto.class);
    }

    @Transactional
    public boolean updateTaskById(Long id, TaskDto taskDto) {
        try {
            dslContext
                    .update(Tables.TASK)
                    .set(Tables.TASK.DESCRIPTION, taskDto.getDescription())
                    .set(Tables.TASK.PRIORITY, taskDto.getPriority())
                    .set(Tables.TASK.PERSON_ID, taskDto.getPersonId())
                    .where(Tables.TASK.ID.eq(id))
                    .returning()
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error in update by id: "
                            + id + ", taskDto: " + taskDto));

            return true;
        } catch (DataAccessException e) {
            log.error(e.getMessage());

            return false;
        }
    }

    public boolean delAllTask() {
        try {
            dslContext
                    .deleteFrom(Tables.TASK)
                    .execute();

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    @Transactional
    public boolean deleteTaskById(Long id) {
        try {
            dslContext
                    .deleteFrom(Tables.TASK)
                    .where(Tables.TASK.ID.eq(id))
                    .execute();

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    private boolean isPriorityValid(Task taskOld, TaskDto taskNew) {

        List<Integer> values = dslContext
                .select(Tables.TASK.PRIORITY)
                .from(Tables.TASK)
                .fetchInto(Integer.class);

        Optional<Integer> minOptional = values.stream().min(Integer::compare);
        Optional<Integer> maxOptional = values.stream().max(Integer::compare);

        if (values.isEmpty()) {
            return true;
        }

        int max = maxOptional.orElseThrow();
        int min = minOptional.orElseThrow();

        if (taskOld.getPriority() == min && taskOld.getPriority() == max) {
            if (taskOld.getPriority() == min && taskNew.getPriority() < min) {
                return false;
            } else return taskOld.getPriority() != max || taskNew.getPriority() <= max;
        } else {
            return true;
        }
    }
}
