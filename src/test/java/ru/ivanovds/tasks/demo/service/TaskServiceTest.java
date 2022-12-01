package ru.ivanovds.tasks.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ivanovds.tasks.demo.dto.TaskDto;
import ru.ivanovds.tasks.demo.entity.Person;
import ru.ivanovds.tasks.demo.entity.Task;

import java.util.List;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Autowired
    PersonService personService;

    @Test
    public void getAllTaskByPersonTest() {
        Person person = personService.getPersonById(1L);
        List<TaskDto> tasks = taskService.getAllTaskByPerson(person);

        Assertions.assertEquals(2, tasks.size());
    }

    @Test
    public void getTaskByIdTest() throws Exception {
        Task task = new Task(1, "Построить ракету");
        TaskDto taskOld = taskService.getTaskById(1L);

        Assertions.assertEquals(task.getDescription(), taskOld.getDescription());
    }

    @Test
    public void updateTaskByIdTest() throws Exception {
        TaskDto task = new TaskDto(3, "Помыть дом");
        taskService.updateTaskById(2L, task);

        TaskDto taskNew = taskService.getTaskById(2L);
        Assertions.assertEquals(task.getDescription(), taskNew.getDescription());
    }

    @Test
    public void updateTaskByIdWithNOValidPriorityTest() throws Exception {
        TaskDto task = new TaskDto(30, "Помыть дом");
        taskService.updateTaskById(26L, task);

        TaskDto taskNew = taskService.getTaskById(26L);
        Assertions.assertNotEquals(taskNew.getPriority(), 30);
    }

    @Test
    public void deleteAllTaskTest() {
        taskService.deleteAllTask();
        List<TaskDto> tasks = taskService.getAllTask();
        Assertions.assertNotEquals(1, tasks.size());
    }

}
