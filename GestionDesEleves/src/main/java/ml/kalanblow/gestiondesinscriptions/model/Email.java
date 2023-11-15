package ml.kalanblow.gestiondesinscriptions.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;
@Embeddable
@ToString
public class Email  implements Serializable {

    @NotNull(message = "{notnull.message}")
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
