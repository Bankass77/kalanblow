package ml.kalanblow.gestiondesinscriptions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.json.GenderDeserializer;
import ml.kalanblow.gestiondesinscriptions.model.json.MaritalStatusDeserializer;
import ml.kalanblow.gestiondesinscriptions.model.json.UserRoleDeserializer;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@Data
@Getter
@Setter
public class User implements Serializable {

    @Embedded
    private UserName userName = new UserName();

    @NotNull(message = "{notnull.message}")
    @JsonDeserialize(using = GenderDeserializer.class)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "{notnull.message}")
    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = MaritalStatusDeserializer.class)
    private MaritalStatus maritalStatus;

    @CreatedDate
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedDate
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @Nullable
    @Embedded
    @Column(name = "user_phoneNumber", unique = true, nullable = false)
    private PhoneNumber user_phoneNumber = new PhoneNumber();

    @Column
    @Nullable
    private byte[] avatar;

    @NotNull(message = "{notnull.message}")
    @Embedded
    @Column(name = "user_email", unique = true, nullable = false)
    private Email userEmail = new Email();

    @NotNull(message = "{notnull.message}")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "etablissement_street")),
            @AttributeOverride(name = "streetNumber", column = @Column(name = "etablissement_streetNumber")),
            @AttributeOverride(name = "codePostale", column = @Column(name = "etablissement_codePostale")),
            @AttributeOverride(name = "city", column = @Column(name = "etablissement_city")),
            @AttributeOverride(name = "country", column = @Column(name = "etablissement_country"))
    })
    private Address address;

    @JsonDeserialize(using = UserRoleDeserializer.class)
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();

    @NotNull(message = "{notnull.message}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Le mot de passe doit être fort.")
    private String password;

}
