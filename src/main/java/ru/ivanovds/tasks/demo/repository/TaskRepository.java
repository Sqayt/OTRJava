package ru.ivanovds.tasks.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ivanovds.tasks.demo.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "select max(t.priority) from Task t")
    Integer findMaximum();

    @Query(value = "select min(t.priority) from Task t")
    Integer findMinimum();
}