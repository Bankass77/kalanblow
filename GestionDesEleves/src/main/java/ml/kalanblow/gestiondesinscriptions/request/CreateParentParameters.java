package ml.kalanblow.gestiondesinscriptions.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public sealed class CreateParentParameters permits EditParentParameters {

    private UserName userName;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private Email email;

    private String profession;

    private MultipartFile avatar;

    private Set<Eleve> enfantsPere = new HashSet<>();

    private Set<Eleve> enfantsMere = new HashSet<>();

    private Address address;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime modifyDate = LocalDateTime.now();

    private String password;

    private PhoneNumber phoneNumber;

}
