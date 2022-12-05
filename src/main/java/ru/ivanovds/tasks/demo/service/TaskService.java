package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.dto.TaskDto;
import ru.ivanovds.tasks.demo.entity.Tables;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Task;
import ru.ivanovds.tasks.demo.repository.impl.TaskRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository repository;

    public TaskDto getTaskById(Long id) throws Exception {
        Task task = repository.findById(id);
        return new TaskDto(task);
    }

    public boolean saveTask(TaskDto taskDto) {
        Task task = new Task(null, taskDto.getPriority(), taskDto.getDescription(), taskDto.getPersonId());
        return repository.insert(task);
    }

    public List<TaskDto> getAllTask() {
        List<Task> tasks = repository.findAll();
        List<TaskDto> taskDtos = new ArrayList<>(tasks.size());

        tasks.forEach(
                it -> {
                    TaskDto taskDto = new TaskDto(it);

                    String fullName = "";

                    try {
                         fullName = repository.findFullNameById(taskDto.getPersonId());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    taskDto.setFullNamePerson(fullName);

                    taskDtos.add(taskDto);
                }
        );

        return taskDtos;
    }

    public boolean updateTaskById(Long id, TaskDto taskDto) {
        Task task = new Task(id, taskDto.getPriority(), taskDto.getDescription(), taskDto.getPersonId());
        return repository.update(id, task);
    }

    public boolean delAllTask() {
        return repository.deleteAll();
    }

    @Transactional
    public boolean deleteTaskById(Long id) {
        return repository.delete(id);
    }

    public String findFullNameById(Long id) throws Exception {
        return repository.findFullNameById(id);
    }

    public String countTaskByPersonId(Long id) {
        return repository.countTaskByPersonId(id);
    }

    public Integer getMaxPriority() {
        return repository.getMaxPriority();
    }

    public Integer getMinPriority() {
        return repository.getMinPriority();
    }
}
