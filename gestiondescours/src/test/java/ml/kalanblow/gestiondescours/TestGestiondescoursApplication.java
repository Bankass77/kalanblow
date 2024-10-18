package ml.kalanblow.gestiondescours;

import org.springframework.boot.SpringApplication;

public class TestGestiondescoursApplication {

	public static void main(String[] args) {
		SpringApplication.from(GestiondescoursApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
