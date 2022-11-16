package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.entity.Person;
import ru.ivanovds.tasks.demo.entity.Task;
import ru.ivanovds.tasks.demo.repository.PersonRepository;
import ru.ivanovds.tasks.demo.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public List<Task> getAllTaskByPerson(Long id) {

        return new ArrayList<>();
    }

    public boolean deleteTaskById(Long id) {
        try {
            Task task = taskRepository.findById(id).orElseThrow();
            taskRepository.delete(task);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateTaskById(Long id, Task task) {
        try {
            Task taskOld = taskRepository.findById(id).orElseThrow();

            taskOld.setDescription(task.getDescription());
            taskOld.setPriority(task.getPriority());

            taskRepository.save(taskOld);

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
