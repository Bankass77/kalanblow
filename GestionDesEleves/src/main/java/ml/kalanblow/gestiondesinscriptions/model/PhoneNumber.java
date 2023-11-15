package ml.kalanblow.gestiondesinscriptions.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;
@Embeddable
@ToString
public class PhoneNumber implements Serializable {

    @Pattern(regexp = "^(\\+223|00223)?[67]\\d{7}$")
    private String phoneNumber;
    // end::class[]

    protected PhoneNumber() {
    }

    public PhoneNumber(String phoneNumber) {
        Assert.hasText(phoneNumber, "Le numéro de téléphone est obligatoire");
        this.phoneNumber = phoneNumber;
    }

    public String asString() {
        return phoneNumber;
    }
}
