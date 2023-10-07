package ml.kalanblow.gestiondesinscriptions.model;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;


@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode
@ToString
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole userRole;
}