package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "school")
@JsonDeserialize(builder = Etablissement.EtablissementBuilder.class)
@Data
public class Etablissement {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long etablisementScolaireId;

    @Version
    Long version;

    @Column
    @NotNull(message = "{notnull.message}")
    @NotBlank
    @Size(min = 2, max = 200)
    private String nomEtablissement;

    @NotNull(message = "{notnull.message}")
    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "street", column = @Column(name = "street")),

            @AttributeOverride(name = "streetNumber", column = @Column(name = "streetNumber")),

            @AttributeOverride(name = "codePostale", column = @Column(name = "codePostale")),

            @AttributeOverride(name = "city", column = @Column(name = "city")), @AttributeOverride(name = "country", column = @Column(name = "country")) })
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
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @Column(name = "telephone_utilisateur", insertable = true, updatable = true, nullable = false)
    @Nullable
    @Embedded
    private PhoneNumber phoneNumber;

    @OneToMany(mappedBy = "etablissement", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Eleve> eleves;

    @OneToMany(mappedBy = "etablissement", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enseignant> enseignants;

    @OneToMany(mappedBy = "etablissement", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Salle> salles;

    private Etablissement(EtablissementBuilder builder) {
        this.address = builder.address;
        this.avatar = builder.avatar;
        this.email = builder.email;
        this.createdDate = builder.createdDate;
        this.lastModifiedDate = builder.lastModifiedDate;
        this.phoneNumber = builder.phoneNumber;
        this.nomEtablissement = builder.nomEtablissement;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class EtablissementBuilder {

        private String nomEtablissement;

        private Address address;

        private byte[] avatar;

        private Email email;

        private LocalDateTime createdDate = LocalDateTime.now();

        private LocalDateTime lastModifiedDate = LocalDateTime.now();

        private PhoneNumber phoneNumber;

        private Set<Eleve> eleves;

        private Set<Enseignant> enseignants;

        private Set<Salle> salles;

        @Version()
        private Long version;

        public EtablissementBuilder nomEtablissement(String nomEtablissement) {

            this.nomEtablissement = nomEtablissement;
            return this;

        }

        public EtablissementBuilder adresse(Address address) {

            this.address = address;
            return this;
        }

        public EtablissementBuilder avatar(byte[] avatar) {

            this.avatar = avatar;
            return this;
        }

        public EtablissementBuilder email(Email email) {

            this.email = email;
            return this;
        }

        public EtablissementBuilder createDate(LocalDateTime createDate) {

            this.createdDate = createdDate;
            return this;
        }

        public EtablissementBuilder phoneNumber(PhoneNumber phoneNumber) {
            this.phoneNumber = phoneNumber;

            return this;
        }

        public EtablissementBuilder lastModiedDate(LocalDateTime lastModifiedDate) {

            this.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public EtablissementBuilder eleves(Set<Eleve> eleves) {
            this.eleves = eleves;

            return this;

        }

        public EtablissementBuilder enseignants(Set<Enseignant> enseignants) {

            this.enseignants = enseignants;

            return this;
        }

        public EtablissementBuilder salles(Set<Salle> salles) {

            this.salles = salles;
            return this;
        }

        public Etablissement build() {

            return new Etablissement(this);
        }
    }

    @JsonDeserialize(builder = EtablissementBuilder.class)
    public static Etablissement creerEtablissementScolaireFromBuilder(EtablissementBuilder builder) {
        return builder.build();

    }
}
