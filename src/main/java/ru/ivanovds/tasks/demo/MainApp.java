package ru.ivanovds.tasks.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ivanovds.tasks.demo.service.PersonService;

@SpringBootApplication(scanBasePackages = {"ru.ivanovds.tasks.demo"})
@RequiredArgsConstructor
public class MainApp {

	private final PersonService personService;

	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}
}
