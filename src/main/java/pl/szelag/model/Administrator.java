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
@DiscriminatorValue("Administrator")
@NamedQuery(name = "Administrator.findAll", query = "SELECT a FROM Administrator a")
@Getter
@Setter
public class Administrator extends Account implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "alarm_phone", length = 4)
    @Size(min = 4, max = 4, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[0-9]*$", message = "{constraint.string.incorrectchar}")
    private String alarmPhone;
}
