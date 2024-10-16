package ml.kalanblow.gestiondesinscriptions.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "chef_etablissement")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChefEtablissement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chefEtablissementId;

    @Column(name = "chefEtablissement_userNmae")
   private UserName userName;

    @Column(name = "chefEtablissement_email")
   private Email email;

    @Column(name = "chefEtablissement_phone")
   private PhoneNumber phoneNumber;
}

