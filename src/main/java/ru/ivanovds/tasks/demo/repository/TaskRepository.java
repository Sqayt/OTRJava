package ru.ivanovds.tasks.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ivanovds.tasks.demo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
