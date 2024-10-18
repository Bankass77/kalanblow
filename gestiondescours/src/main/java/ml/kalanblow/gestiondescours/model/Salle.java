package ml.kalanblow.gestiondescours.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "salle")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Salle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salleId; // Identifiant unique de la salle

    @NotNull
    @Column(nullable = false, unique = true)
    private String nomSalle; // Nom ou numéro de la salle (ex. "Salle 101")

    @Column(nullable = true)
    private String batiment; // Bâtiment où se trouve la salle (ex. "Bâtiment A")

    @Column(nullable = true)
    private int capacite; // Capacité de la salle (nombre de places disponibles)

    private Long etablissementId; // Etablissement auquel la salle appartient:Référence à l'établissement (IDs provenant du service Gestion des Élèves)

    @OneToMany(mappedBy = "salle", fetch = FetchType.EAGER)
    private List<Cours> cours; // Liste des cours se déroulant dans cette salle

    @Column(nullable = true)
    private boolean equipementSpecial; // Indicateur si la salle a un équipement spécial (ex. projecteur, ordinateurs)

    @Enumerated(EnumType.STRING)
    private TypeSalle typeSalle;

}
