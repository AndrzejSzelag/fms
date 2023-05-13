package pl.szelag.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public abstract class Account extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_GENERATOR")
    @SequenceGenerator(name = "ACCOUNT_GENERATOR", sequenceName = "ACCOUNT_SEQ", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "login", length = 32, nullable = false, unique = true, updatable = false)
    @Size(min = 5, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\.'_-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    @ToString.Include
    private String login;

    @Getter
    @Setter
    @ToString.Exclude
    private String type;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "password", length = 64, nullable = false)
    @Size(min = 8, max = 64, message = "{constraint.string.length.notinrange}") // max dla SHA3-256
    @Pattern(regexp = "^[\\p{L}\\p{N}\\._-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    @ToString.Exclude
    private String password;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "first_name", length = 32, nullable = false)
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\'-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    @ToString.Exclude
    private String firstName;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "last_name", length = 32, nullable = false)
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\'-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    @ToString.Exclude
    private String lastName;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "phone", length = 9, nullable = false)
    @Size(min = 4, max = 9, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[0-9]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    @ToString.Exclude
    private String phone;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "activated", nullable = false)
    @Getter
    @Setter
    @ToString.Exclude
    private boolean isActivated;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "confirmed", nullable = false)
    @Getter
    @Setter
    @ToString.Exclude
    private boolean isConfirmed;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected Object getBusinessKey() {
        return login;
    }
}
