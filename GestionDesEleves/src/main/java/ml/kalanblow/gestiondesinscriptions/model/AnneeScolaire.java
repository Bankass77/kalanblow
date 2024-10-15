package ml.kalanblow.gestiondesinscriptions.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "anneeScolaire")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class AnneeScolaire {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anneeScolaireId;

    @Version()
    private Long version;

    @Column
    @NotNull(message = "{notnull.message}")
    private int anneeScolaireDebut;

    @Column
    @NotNull(message = "{notnull.message}")
    private int anneeScolaireFin;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "anneeScolaire")
    //@JoinColumn( name ="eleve_classe")
    private Set<Classe> classes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "annee_eleve")
    private Set<Eleve> eleves = new HashSet<>();
}
