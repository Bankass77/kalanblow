package ml.kalanblow.gestiondesplanning;

import org.springframework.boot.SpringApplication;

public class TestGestiondesplanningApplication {

	public static void main(String[] args) {
		SpringApplication.from(GestiondesplanningApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
