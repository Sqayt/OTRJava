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

    @DeleteMapping("/{idPerson}/{idTask}")
    public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable Long idPerson,
                                                     @PathVariable Long idTask) {
        if (service.deleteTaskFromPersonById(idPerson, idTask)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateTaskById(@PathVariable Long id,
                                            @RequestBody TaskDto taskDto) {
        if (service.updateTaskById(id, taskDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAllTask() {
        if (service.deleteAllTask()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
