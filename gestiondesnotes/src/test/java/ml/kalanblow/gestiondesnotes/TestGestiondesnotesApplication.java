package ml.kalanblow.gestiondesnotes;

import org.springframework.boot.SpringApplication;

public class TestGestiondesnotesApplication {

	public static void main(String[] args) {
		SpringApplication.from(GestiondesnotesApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
