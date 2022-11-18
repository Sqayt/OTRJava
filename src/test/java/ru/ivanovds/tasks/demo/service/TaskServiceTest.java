package ru.ivanovds.tasks.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ivanovds.tasks.demo.entity.Person;
import ru.ivanovds.tasks.demo.entity.Task;

import java.util.List;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Autowired
    PersonService personService;

    //TODO не работает
    @Test
    public void createTaskByNewPersonTest() {
        Task task = new Task(17,"It is test case");
        Person person = new Person("Начальник", "Михаил", "Задорнов", "Сергеевич", "ОАО");
        List<Task> tasks = taskService.getAllTask();

        taskService.saveTaskByPerson(task, person);
        List<Task> tasksNew = taskService.getAllTask();

        Assertions.assertNotEquals(tasksNew.size(), tasks.size());
    }

    @Test
    public void getAllTaskByPersonTest() {
        Person person = personService.getPersonById(1L).orElseThrow();
        List<Task> tasks = taskService.getAllTaskByPerson(person);

        Assertions.assertEquals(2, tasks.size());
    }

    @Test
    public void getTaskByIdTest() {
        Task task = new Task(1, "Построить ракету");
        Task taskOld = taskService.getTaskById(1L).orElse(new Task());

        Assertions.assertEquals(task.getDescription(), taskOld.getDescription());
    }

    @Test
    public void deleteTaskByIdTest() {
        taskService.deleteTaskById(1L);
        Person person = personService.getPersonById(1L).orElse(new Person());

        Assertions.assertNotEquals(person.getName(), null);
    }

    @Test
    public void updateTaskByIdTest() {
        Task task = new Task(3, "Помыть дом");
        taskService.updateTaskById(2L, task);

        Task taskNew = taskService.getTaskById(2L).orElse(new Task());
        Assertions.assertEquals(task.getDescription(), taskNew.getDescription());
    }

    @Test
    public void deleteAllTaskTest() {
        taskService.deleteAllTask();
        List<Task> tasks = taskService.getAllTask();
        Assertions.assertNotEquals(1, tasks.size());
    }

}
