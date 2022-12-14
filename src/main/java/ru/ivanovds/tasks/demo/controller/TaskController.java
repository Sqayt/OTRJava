package ru.ivanovds.tasks.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivanovds.tasks.demo.dto.TaskDto;
import ru.ivanovds.tasks.demo.service.TaskService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        try {
            TaskDto taskDto = service.getTaskById(id);

            return new ResponseEntity<>(taskDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllTask() {
        try {
            List<TaskDto> tasks = service.getAllTask();

            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> saveTask(@RequestBody TaskDto task) {
        return service.saveTask(task) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @DeleteMapping("/{idTask}")
    public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable Long idTask) {
        return service.deleteTaskById(idTask) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateTaskById(@PathVariable Long id,
                                            @RequestBody TaskDto taskDto) {
        return service.updateTaskById(id, taskDto) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAllTask() {
        return service.delAllTask() ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
