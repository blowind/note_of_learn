package com.zxf.bootdata;

import com.zxf.bootdata.dao.PersonRepository;
import com.zxf.bootdata.model.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;
import java.util.List;

import static com.zxf.bootdata.specs.CustomerSpecs.*;

@SpringBootApplication
@EnableJpaRepositories
public class BootdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootdataApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(PersonRepository repository) {
		return (args) -> {
			repository.save(new Person("a", 10, "杭州", new Date()));
			repository.save(new Person("b", 20, "合肥", new Date()));
			repository.save(new Person("c", 30, "南京", new Date()));

			/* 使用JpaRepository接口默认的findAll方法查询  */
			for(Person person : repository.findAll()) {
				System.out.println(person.toString());
			}

			System.out.println("==============split================");

			for(Person person : repository.findAll(new Sort(Sort.Direction.DESC, "age"))) {
				System.out.println(person.toString());
			}

			repository.findById(1L).ifPresent(
					person -> {
						System.out.println("findById(1L): " + person.toString());
					});

			System.out.println("==============split================");
			/* 使用JpaSpecificationExecutor接口的spec方式查询 */
			List<Person> people = repository.findAll(personFromHangzhou());
			for(Person p : people) {
				System.out.println(p);
			}
			System.out.println("==============split================");
			List<Person> people1 = repository.findAll(personFromChangSanJiao());
			for(Person p : people1) {
				System.out.println(p);
			}
			System.out.println("==============split================");
			List<Person> people2 = repository.findAll(youngPersonFromNanjing());
			for(Person p : people2) {
				System.out.println(p);
			}

		};
	}
}
