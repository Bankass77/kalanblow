package ml.kalanblow.gestiondescours;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@Disabled
class GestiondescoursApplicationTests {

	@Test
	void contextLoads() {
	}

}