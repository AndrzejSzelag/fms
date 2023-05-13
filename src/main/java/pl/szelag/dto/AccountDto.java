package pl.szelag.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class AccountDto implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    @ToString.Exclude
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 5, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\.'_-]*$", message = "{constraint.string.incorrectchar}")
    @Setter
    @ToString.Include
    private String login;

    @NotNull(message = "{constraint.notnull}")
    @Setter
    @ToString.Include
    private String type;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 8, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\._-]*$", message = "{constraint.string.incorrectchar}")
    @Setter
    @ToString.Exclude
    private String password;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\'-]*$", message = "{constraint.string.incorrectchar}")
    @Setter
    @ToString.Include
    private String firstName;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\'-]*$", message = "{constraint.string.incorrectchar}")
    @Setter
    @ToString.Include
    private String lastName;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 4, max = 9, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[0-9]*$", message = "{constraint.string.incorrectchar}")
    @Setter
    @ToString.Exclude
    private String phone;

    @NotNull(message = "{constraint.notnull}")
    @Setter
    @ToString.Exclude
    private boolean isActivated;

    @NotNull(message = "{constraint.notnull}")
    @Setter
    @ToString.Exclude
    private boolean isConfirmed;

    public AccountDto(Long id, String login, String type, String firstName, String lastName, String phone, boolean isActivated, boolean isConfirmed) {
        this.id = id;
        this.login = login;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.isActivated = isActivated;
        this.isConfirmed = isConfirmed;
    }
}
