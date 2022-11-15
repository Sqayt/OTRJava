package ru.ivanovds.tasks.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivanovds.tasks.demo.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
