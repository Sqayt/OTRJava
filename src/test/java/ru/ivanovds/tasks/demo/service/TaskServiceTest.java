package ru.ivanovds.tasks.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ivanovds.tasks.demo.dto.TaskDto;
import ru.ivanovds.tasks.demo.entity.Tables;

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
        TaskDto taskDto = new TaskDto(10, "TestSave", 48L);
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
         TaskDto taskDto = new TaskDto(123, "Test Update", 48L);
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

    @Test
    public void findFullNameByIdTest() throws Exception {
        Long id = 1L;
        String fullName = taskService.findFullNameById(id);

        Assertions.assertNotEquals(fullName, "");
        Assertions.assertNotNull(fullName);
    }

    @Test
    public void countTaskByPersonIdTest() {
        String count = taskService.countTaskByPersonId(1L);

        Assertions.assertEquals("2", count);
    }

    @Test
    public void getMaxPriorityTest() {
        Integer max = taskService.getMaxPriority();

        Assertions.assertEquals(229, max);
    }

    @Test
    public void getMinPriorityTest() {
        Integer min = taskService.getMinPriority();

        Assertions.assertEquals(2, min);
    }
}
