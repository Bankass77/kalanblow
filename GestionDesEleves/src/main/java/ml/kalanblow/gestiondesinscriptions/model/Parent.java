package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Table(name = "parent")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@JsonDeserialize(builder = Parent.ParentBuilder.class)
public class Parent implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    @Embedded
    private  User user;

    @NotNull(message = "{notnull.message}")
    private String profession;

    @JsonManagedReference
    @OneToMany(mappedBy = "pere", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Eleve> enfantsPere = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "mere", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Eleve> enfantsMere = new HashSet<>();


}
