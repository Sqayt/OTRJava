package ru.ivanovds.tasks.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanovds.tasks.demo.entity.Person;
import ru.ivanovds.tasks.demo.entity.Task;
import ru.ivanovds.tasks.demo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    // TODO не работает
    public boolean saveTaskByPerson(Task task, Person person) {
        try {
            task.setPerson(person);
            taskRepository.save(task);

            return true;
        } catch (Exception e) {
            return false;
        }
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
