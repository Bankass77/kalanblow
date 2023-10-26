/*
package ml.kalanblow.gestiondesinscriptions.model;

import jakarta.persistence.*;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;

import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Set<UserRole> userRole;
}
*/
