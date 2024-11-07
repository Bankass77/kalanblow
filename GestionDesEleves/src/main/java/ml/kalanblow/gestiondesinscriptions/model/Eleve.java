package ml.kalanblow.gestiondesinscriptions.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ml.kalanblow.gestiondesinscriptions.enums.EtatEleve;

@Entity
@Table(name = "eleve")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Eleve implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eleveId;

    @Version()
    private Long version;

    @Embedded
    private User user;

    @Column(name = "ine_number")
    @NotNull(message = "{notnull.message}")
    private String ineNumber;

    @NotNull(message = "{notnull.message}")
    @Column(name = "birthDate")
    @Past
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateDeNaissance;

    @Column(name = "age")
    @NotNull(message = "{notnull.message}")
    private int age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "eleve_parent",
            joinColumns = @JoinColumn(name = "eleveId"),
            inverseJoinColumns = @JoinColumn(name = "parentId")
    )
    private Set<Parent> parents = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "etablisementScolaireId", nullable = false, referencedColumnName = "etablisementScolaireId")
    @JsonIgnore
    private Etablissement etablissement;

    @Column
    private LocalDateTime dateInscription = LocalDateTime.now();

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{notnull.message}")
    private EtatEleve etat;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "classeId", nullable = false,referencedColumnName ="classeId" )
    @JsonIgnore
    private Classe classeActuelle;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "eleve_anneehistorique")
    private List<AnneeScolaire> historiqueScolaires;
}
