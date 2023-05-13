package pl.szelag.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class ElectricianDto extends AccountDto {

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 3, message = "{constraint.string.length.toolong}")
    @Pattern(regexp = "^[A-z0-9]*$", message = "{constraint.string.incorrectchar}")
    private String department;

    public ElectricianDto(Long id, String login, String type, String firstName, String lastName,
            String phone, boolean isActivated, boolean isConfirmed, String department) {
        super(id, login, type, firstName, lastName, phone, isActivated, isConfirmed);
        this.department = department;
    }
}
