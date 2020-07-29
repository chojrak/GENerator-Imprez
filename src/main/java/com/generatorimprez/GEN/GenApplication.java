package com.generatorimprez.GEN;

import com.generatorimprez.GEN.Model.Mail;
import com.generatorimprez.GEN.Model.Postgres;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenApplication {

	public static void main(String[] args) {
		Postgres.start();
		SpringApplication.run(GenApplication.class, args);



	}

}
