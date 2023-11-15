package ml.kalanblow.gestiondesinscriptions.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class Address implements Serializable {
    /**
     *{{
     */
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{notnull.message}")
    private String street;

    @Digits(integer = 3, fraction = 0)
    @NotNull(message = "{notnull.message}")
    private int streetNumber;

    private String city;

    @Pattern(regexp = "^(?:0[1-9]|[1-8]\\d|9[0-8])\\d{3}$")
    @NotNull(message = "{notnull.message}")
    private Integer codePostale;

    @NotNull(message = "{notnull.message}")
    private String country;

    public String getFullAdresse() {

        return  String.format("%s %d  %s %d %s", street, streetNumber, city, codePostale,country);
    }
}
