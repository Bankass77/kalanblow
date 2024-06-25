package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("PARENTS")
@Table(name = "parent")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@JsonDeserialize(builder = Parent.ParentBuilder.class)
public class Parent extends  User{

    @NotNull(message = "{notnull.message}")
    private String profession;

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "pere", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Eleve> enfantsPere = new HashSet<>();

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "mere", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Eleve> enfantsMere = new HashSet<>();


}
