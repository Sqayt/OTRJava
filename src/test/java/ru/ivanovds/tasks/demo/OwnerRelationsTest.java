package ru.ivanovds.tasks.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ivanovds.tasks.demo.repository.PersonRepository;
import ru.ivanovds.tasks.demo.repository.TaskRepository;

@SpringBootTest
public class OwnerRelationsTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void inverseEndTest() {
        
    }
}
