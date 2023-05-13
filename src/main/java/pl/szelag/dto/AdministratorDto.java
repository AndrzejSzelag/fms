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
public class AdministratorDto extends AccountDto {

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 4, max = 4, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[0-9]*$", message = "{constraint.string.incorrectchar}")
    private String alarmPhone;

    public AdministratorDto(Long id, String login, String type, String firstName, String lastName,
            String phone, boolean isActivated, boolean isConfirmed, String alarmPhone) {
        super(id, login, type, firstName, lastName, phone, isActivated, isConfirmed);
        this.alarmPhone = alarmPhone;
    }
}
