package ml.kalanblow.gestiondesinscriptions.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
@Data
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserName  implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String prenom;

    private String nomDeFamille;

    public String getFullName() {

        return  String.format("%s %s", prenom, nomDeFamille);
    }
}
