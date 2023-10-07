package ml.kalanblow.gestiondesinscriptions.model;

import jakarta.persistence.Embeddable;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;
@Embeddable
@ToString
public class PhoneNumber implements Serializable {

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
