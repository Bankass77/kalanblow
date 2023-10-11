package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;




/**
 *
 */


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
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
