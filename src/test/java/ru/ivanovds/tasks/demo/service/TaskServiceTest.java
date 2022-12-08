package ru.ivanovds.tasks.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ivanovds.tasks.demo.dto.TaskDto;

import java.util.List;

@SpringBootTest
@Slf4j
public class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Autowired
    DSLContext context;

    @Test
    public void getAllTasksTest() {
        List<TaskDto> tasks = taskService.getAllTask();

        Assertions.assertNotEquals(tasks.size(), 0);
    }

    @Test
    public void saveTaskTest() {
        TaskDto taskDto = new TaskDto(10, "TestSave", "48");
        taskService.saveTask(taskDto);

        List<TaskDto> tasks = taskService.getAllTask();
        TaskDto task = tasks.get(tasks.size() - 1);

        Assertions.assertEquals(task.getDescription(), taskDto.getDescription());
    }

    @Test
    public void getTaskByIdTest() throws Exception {
        TaskDto taskDto = taskService.getTaskById(42L);

        Assertions.assertEquals(taskDto.getDescription(), "Test");
    }

    @Test
    public void updateTaskByIdTest() throws Exception {
         TaskDto taskDto = new TaskDto(123, "Test Update", "48");
         Long id = 42L;

         taskService.updateTaskById(id, taskDto);

         TaskDto taskDtoNew = taskService.getTaskById(id);

         Assertions.assertEquals(taskDtoNew.getDescription(), taskDto.getDescription());
    }

    @Test
    public void deleteTaskByIdTest() {
        taskService.deleteTaskById(42L);

        List<TaskDto> tasks = taskService.getAllTask();

        Assertions.assertEquals(1, tasks.size());
    }

    @Test
    public void delAllTasksTest() {
        taskService.delAllTask();

        List<TaskDto> tasks = taskService.getAllTask();

        Assertions.assertEquals(0, tasks.size());
    }
}
