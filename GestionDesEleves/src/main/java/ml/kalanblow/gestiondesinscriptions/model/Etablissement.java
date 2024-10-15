package ml.kalanblow.gestiondesinscriptions.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "school")
@Data
public class Etablissement implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long etablisementScolaireId;

    @Version
    Long version;

    @Column
    @NotNull(message = "{notnull.message}")
    @Size(min = 2, max = 200)
    private String nomEtablissement;

    @NotNull
    @Column
    @Size(min = 8, max = 18)
    private String identiantEtablissement;

    @NotNull(message = "{notnull.message}")
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "street", column = @Column(name = "street")),

            @AttributeOverride(name = "streetNumber", column = @Column(name = "streetNumber")),

            @AttributeOverride(name = "codePostale", column = @Column(name = "codePostale")),

            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "country", column = @Column(name = "country"))})
    private Address address;

    @Column
    @Nullable
    private byte[] avatar;

    @NotNull(message = "{notnull.message}")
    @Column(unique = true, nullable = false, updatable = true, name = "email")
    @Embedded
    private Email email;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    //@JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
   // @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @Column(name = "telephone_utilisateur", insertable = true, updatable = true, nullable = false)
    @Nullable
    @Embedded
    private PhoneNumber phoneNumber;

    @OneToMany(mappedBy = "etablissement", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Eleve> eleves;

    @OneToMany(mappedBy = "etablissement", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Enseignant> enseignants;

    @OneToMany(mappedBy = "etablissement", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Classe> classes;

}
