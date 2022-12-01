package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.dto.TaskDto;
import ru.ivanovds.tasks.demo.entity.Person;
import ru.ivanovds.tasks.demo.entity.Task;
import ru.ivanovds.tasks.demo.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskDto getTaskById(Long id) throws Exception {
        try {
            Task task = taskRepository.findById(id).orElseThrow();

            return new TaskDto(task);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new Exception("Ошибка в сервисе");
        }
    }

    public List<TaskDto> getAllTaskByPerson(Person person) {
        List<Task> tasks = person.getTasks();
        List<TaskDto> tasksDto = new ArrayList<>(tasks.size());

        tasks.forEach(
                it -> tasksDto.add(new TaskDto(it))
        );

        return tasksDto;
    }

    public List<TaskDto> getAllTask() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto> tasksDto = new ArrayList<>();

        tasks.forEach(
                it -> tasksDto.add(new TaskDto(it))
        );

        return tasksDto;
    }

    public boolean updateTaskById(Long id, TaskDto task) {
        try {
            Task taskOld = taskRepository.findById(id).orElseThrow();

            taskOld.setDescription(task.getDescription());
            taskOld.setPriority(task.getPriority());

            taskRepository.save(taskOld);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());

            return false;
        }
    }

    public boolean deleteTaskById(Long id) {
        try {
            taskRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteAllTask() {
        try {
            taskRepository.deleteAll();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
