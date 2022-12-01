package ru.ivanovds.tasks.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivanovds.tasks.demo.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    void deleteTaskById(Long id);
}
