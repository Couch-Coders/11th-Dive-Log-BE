package kr.couchcoding.divelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class DiveLogApplication {

	public static void main(String[] args) {
		log.info("database url : " + System.getenv("JDBC_DATABASE_URL"));
		SpringApplication.run(DiveLogApplication.class, args);
	}

}
