package it.beachill;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BeachillApplication {
	@Bean
	public CommandLineRunner lineRunner(){
		return runner -> {
			System.out.println("Sto eseguendo un cazzetto");
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(BeachillApplication.class, args);
	}
}
