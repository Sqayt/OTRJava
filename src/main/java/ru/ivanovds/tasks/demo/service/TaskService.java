package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.dto.TaskDto;
import ru.ivanovds.tasks.demo.entity.tables.pojos.Task;
import ru.ivanovds.tasks.demo.repository.impl.TaskRepository;

import java.util.ArrayList;
import java.util.List;

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
        long idPerson = convertToStr(taskDto.getFullNamePerson());

        if (!isValidTaskDto(taskDto)) {
            return false;
        }

        Task task = new Task(null, taskDto.getPriority(), taskDto.getDescription(), idPerson);

        return repository.insert(task);
    }

    public List<TaskDto> getAllTask() {
        List<Task> tasks = repository.findAll();
        List<TaskDto> tasksDto = new ArrayList<>(tasks.size());

        tasks.forEach(
                it -> {
                    TaskDto taskDto = new TaskDto(it);
                    long id = convertToStr(taskDto.getFullNamePerson());

                    String fullName;

                    try {
                         fullName = repository.findFullNameById(id);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    taskDto.setFullNamePerson(fullName);

                    tasksDto.add(taskDto);
                }
        );

        return tasksDto;
    }

    public boolean updateTaskById(Long id, TaskDto taskDto) {
        long idPerson = convertToStr(taskDto.getFullNamePerson());

        long maxPriority = getMaxPriority();
        long minPriority = getMinPriority();

        try {
            Task taskOld = repository.findById(id);
            if (
                    (taskOld.getPriority() == maxPriority && taskDto.getPriority() > maxPriority) ||
                    (taskOld.getPriority() == minPriority && taskDto.getPriority() < minPriority)
            ) {
                taskDto.setPriority(taskOld.getPriority());
            }
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }

        Task task = new Task(id, taskDto.getPriority(), taskDto.getDescription(), idPerson);
        return repository.update(id, task);
    }

    public boolean delAllTask() {
        return repository.deleteAll();
    }

    public boolean deleteTaskById(Long id) {
        return repository.delete(id);
    }

    private boolean isValidTaskDto(TaskDto taskDto) {
        return  !taskDto.getDescription().isEmpty() && !taskDto.getDescription().equals("");
    }

    private Long convertToStr(String fullNamePerson) {
        long id;

        if (fullNamePerson == null || fullNamePerson.isEmpty()) {
            id = 0L;
        } else {
            id = Long.parseLong(fullNamePerson);
        }

        return id;
    }

    private Integer getMaxPriority() {
        return repository.getMaxPriority();
    }

    private Integer getMinPriority() {
        return repository.getMinPriority();
    }
}
