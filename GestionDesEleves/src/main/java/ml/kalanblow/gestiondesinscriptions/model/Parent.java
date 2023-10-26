package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
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
@Entity
@JsonDeserialize(builder = Parent.ParentBuilder.class)
public class Parent extends  User{

    private String profession;

    @JsonManagedReference
    @OneToMany(mappedBy = "pere", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Eleve> enfantsPere = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "mere", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Eleve> enfantsMere = new HashSet<>();


}
