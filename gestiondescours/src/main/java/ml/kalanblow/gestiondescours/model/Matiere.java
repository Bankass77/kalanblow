package ml.kalanblow.gestiondescours.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "matiere")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Matiere implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matiereId;

    @NotNull(message = "{notnull.message}")
    @Size(min = 2, max = 100)
    private String nom;

    @Column(length = 500)
    private String description;

    @NotNull(message = "{notnull.message}")
    private int coefficient;

    @NotNull(message = "{notnull.message}")
    private int nombreHeures; // Nombre d'heures hebdomadaires allouées à la matière

    @ElementCollection
    private List<EnseignantCours> enseignantResponsableMatieres; // Enseignant responsable de cette matière
}
