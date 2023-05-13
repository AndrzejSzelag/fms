package pl.szelag.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@DiscriminatorValue("Supervision")
@NamedQuery(name = "Supervision.findLogin", query = "SELECT s FROM Supervision s where s.login = :login")
@Getter
@Setter
public class Supervision extends Account implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "department", length = 3)
    @Size(max = 3, message = "{constraint.string.length.toolong}")
    @Pattern(regexp = "^[A-z0-9]*$", message = "{constraint.string.incorrectchar}")
    private String department;
}
