package ml.kalanblow.gestiondesinscriptions.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Disabled
public class EleveTest {

    private Eleve eleve;

    @BeforeEach
    public void setUp(){
        eleve= new Eleve();
    }
    @Test
    public void testGetSetIneNumber() {
        eleve= new Eleve.EleveBuilder().build();
        eleve.setIneNumber("YTTYTT987899");
        assertEquals("YTTYTT987899", eleve.getIneNumber());
    }

    @Test
    public void testGetSetDateDeNaissance() {
      eleve = new Eleve.EleveBuilder().build();
      eleve.setDateDeNaissance(LocalDate.of(1980, 6, 23));
        assertEquals(LocalDate.of(1980, 6, 23), eleve.getDateDeNaissance());
    }

    @Test
    public void testGetSetAge() {
     eleve = new Eleve.EleveBuilder().build();
     eleve.setAge(25);
        assertEquals(25, eleve.getAge());
    }


}
