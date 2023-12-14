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
@Entity
@JsonDeserialize(builder = Parent.ParentBuilder.class)
public class Parent extends  User{

    @NotNull(message = "{parent.profession.not.null}")
    private String profession;

    @JsonManagedReference
    @OneToMany(mappedBy = "pere", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Eleve> enfantsPere = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "mere", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Eleve> enfantsMere = new HashSet<>();

    public void addEnfantPere(Eleve eleve) {
        enfantsPere.add(eleve);
        eleve.setPere(this);
    }

    public void removeEnfantPere(Eleve eleve) {
        enfantsPere.remove(eleve);
        eleve.setPere(null);
    }

    public void addEnfantMere(Eleve eleve) {
        enfantsMere.add(eleve);
        eleve.setMere(this);
    }

    public void removeEnfantMere(Eleve eleve) {
        enfantsMere.remove(eleve);
        eleve.setMere(null);
    }


}
