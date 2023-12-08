package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.util.converter.GenderConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;


@Data
@Entity
@Table(name = "kalanblowUtilisateur", uniqueConstraints = @UniqueConstraint(columnNames = "id"), indexes = @Index(name = "idx_user_email", columnList = "email", unique = true))
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "USER_TYPE")
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@JsonDeserialize(builder = User.UserBuilder.class)
public abstract class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version()
    private Long version;


    @Embedded
    private UserName userName;


    @Column(name = "gender")
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_Status")
    private MaritalStatus maritalStatus;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime lastModifiedDate ;

    @Column(name = "telephone_utilisateur", insertable = true, updatable = true, nullable = false)
    @Nullable
    @Embedded
    private PhoneNumber phoneNumber;

    @Column
    @Nullable
    private byte[] avatar;

    
    @Column(unique = true, nullable = false, updatable = true, name = "email")
    @Embedded
    private Email email;

    
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "street", column = @Column(name = "street")),

            @AttributeOverride(name = "streetNumber", column = @Column(name = "streetNumber")),

            @AttributeOverride(name = "codePostale", column = @Column(name = "codePostale")),

            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "country", column = @Column(name = "country"))})
    private Address address;



    @ElementCollection(targetClass = UserRole.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles")
    @Column(name = "role")
    @JsonManagedReference
    private Set<UserRole> roles;

    @NotNull
    @Column(name = "password")
    private String password;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {

        private UserName userName;

        private Gender gender;

        private MaritalStatus maritalStatus;

        private LocalDateTime createdDate = LocalDateTime.now();

        private LocalDateTime lastModifiedDate = LocalDateTime.now();
        private PhoneNumber phoneNumber;
        private byte[] avatar;

        private Email email;

        private Address address;

        private Set<UserRole> roles;

        private String password;

    }

    public String getPhoneNumberAsString() {
        return phoneNumber != null ? phoneNumber.asString() : "";
    }


}
