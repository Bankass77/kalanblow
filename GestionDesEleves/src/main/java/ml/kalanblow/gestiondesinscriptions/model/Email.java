package ml.kalanblow.gestiondesinscriptions.model;

import jakarta.persistence.Embeddable;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;
@Embeddable
@ToString
public class Email  implements Serializable {
    private String email;

    protected Email() {
    }

    public Email(String email) {
        Assert.hasText(email, "email cannot be blank");
        Assert.isTrue(email.contains("@"), "email should contain @ symbol");
        this.email = email;
    }

    public String asString() {
        return email;
    }

}
