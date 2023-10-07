package ml.kalanblow.gestiondesinscriptions.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@JsonDeserialize(builder = EtablissementScolaire.EtablissementScolaireBuilder.class)
public class EtablissementScolaire {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long etablisementScolaireId;

    @Column
    @NotNull
    private String nomEtablissement;

    @NotNull(message = "Address is required")
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

    @NotNull(message = "Please enter a valid address email.")
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

    @OneToMany(mappedBy = "etablissementScolaire")
    private Set<Eleve> eleves;

    private EtablissementScolaire (EtablissementScolaireBuilder builder){
        this.address=builder.address;
        this.avatar=builder.avatar;
        this.email=builder.email;
        this.createdDate=builder.createdDate;
        this.lastModifiedDate=builder.lastModifiedDate;
        this.phoneNumber=builder.phoneNumber;
    }


    @JsonPOJOBuilder(withPrefix = "")
    public static  class  EtablissementScolaireBuilder{
        private Address address;
        private byte[] avatar;
        private Email email;
        private LocalDateTime createdDate = LocalDateTime.now();
        private LocalDateTime lastModifiedDate = LocalDateTime.now();
        private PhoneNumber phoneNumber;

        public EtablissementScolaire build(){

            return new EtablissementScolaire(this);
        }
    }

    @JsonDeserialize(builder=EtablissementScolaireBuilder.class)
    public static  EtablissementScolaire creerEtablissementScolaireFromBuilder(EtablissementScolaireBuilder builder) {
        return builder.build();

    }
}
