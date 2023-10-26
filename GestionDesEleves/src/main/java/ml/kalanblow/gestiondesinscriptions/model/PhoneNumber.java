package ml.kalanblow.gestiondesinscriptions.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;
@Embeddable
@ToString
public class PhoneNumber implements Serializable {

    @Pattern(regexp = "^(00223|\\+223)[67]\\d{6}$")
    private String phoneNumber;
    // end::class[]

    protected PhoneNumber() {
    }

    public PhoneNumber(String phoneNumber) {
        Assert.hasText(phoneNumber, "phoneNumber cannot be blank");
        this.phoneNumber = phoneNumber;
    }

    public String asString() {
        return phoneNumber;
    }
}
