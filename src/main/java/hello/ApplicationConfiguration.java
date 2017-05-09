package hello;

import java.io.File;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration //needed for DB autoconfig
@EnableJpaRepositories("hello") //should point to the package where the repositories are :)
public class ApplicationConfiguration {
	
	@Bean
	public EntityManagerFactory entityManagerFactory () {
		return Persistence.createEntityManagerFactory("JPALockDemo");
	}	
	
}
