package com.zxf.bootdata;

import com.zxf.bootdata.dao.PersonRepository;
import com.zxf.bootdata.model.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.zxf.bootdata")
public class BootdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootdataApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(PersonRepository repository) {
		return (args) -> {
			repository.save(new Person("a", 1, "I"));
			repository.save(new Person("b", 2, "II"));
			repository.save(new Person("c", 3, "III"));

			for(Person person : repository.findAll()) {
				System.out.println(person.toString());
			}

			repository.findById(1L).ifPresent(
					person -> {
						System.out.println("findById(1L): " + person.toString());
			});
		};
	}
}
