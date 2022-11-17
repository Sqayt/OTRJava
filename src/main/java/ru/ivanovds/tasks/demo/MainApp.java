package ru.ivanovds.tasks.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ivanovds.tasks.demo.entity.Person;
import ru.ivanovds.tasks.demo.entity.Task;
import ru.ivanovds.tasks.demo.repository.PersonRepository;
import ru.ivanovds.tasks.demo.service.PersonService;
import ru.ivanovds.tasks.demo.service.TaskService;

import java.util.List;

@SpringBootApplication(scanBasePackages = {"ru.ivanovds.tasks.demo"})
@RequiredArgsConstructor
public class MainApp implements CommandLineRunner {

	private final PersonService personService;
	private final TaskService taskService;

	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Person michail = new Person("Начальник", "Михаил", "Задорнов", "Сергеевич", "ОАО");
		Person sacha = new Person("Работник", "Александр", "Жуйков", "Сергеевич", "Кипр");
		Person lisa = new Person("Бухгалтер", "Елизавета", "Казакова", "Владимирована", "Дубай");

		Task cleanTerritory = new Task(1,"Убрать территорию");
		Task createRocket = new Task(29, "Построить ракету");
		Task flyOnPlane = new Task(2,"Полетать на самолете");

		michail.setTasks(List.of(createRocket, flyOnPlane));
		lisa.setTasks(List.of(cleanTerritory));

		List<Person> people = List.of(michail, sacha, lisa);
		people.forEach(personService::savePerson);

		personService.savePerson(sacha);
	}
}
