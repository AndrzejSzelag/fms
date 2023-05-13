package pl.szelag.account;

import lombok.Getter;
import lombok.Setter;
import pl.szelag.dto.AdministratorDto;
import pl.szelag.endpoint.AccountEndpoint;
import pl.szelag.exception.AccountException;
import pl.szelag.exception.AppBaseException;
import pl.szelag.util.ContextUtil;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@ViewScoped
@Named(value = "createAdministrator")
public class CreateAdministrator implements Serializable {

    private static final String MESSAGE_ID_FORM = "form:infoForm";

    @EJB
    private AccountEndpoint accountEndpoint;

    @Getter
    private final AdministratorDto administratorDto = new AdministratorDto();

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 8, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\._-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String passwordRepeat = "";

    public String createNewAdministrator() throws AppBaseException {
        if (administratorDto.getPassword().equals(passwordRepeat)) {
            return createNewAccountForAdministrator(administratorDto);
        } else {
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.PASSWORDS_NOT_MATCH);
            return "";
        }
    }

    private String createNewAccountForAdministrator(final AdministratorDto administratorDto) throws AppBaseException {
        try {
            return addNewAccountForAdministrator(administratorDto);
        } catch (AccountException ex) {
            Logger.getLogger(CreateAdministrator.class.getName()).log(Level.SEVERE, "Report an exception in CreateAdministrator.createNewAccountForAdministrator(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.GIVEN_LOGIN_ALREADY_EXIST);
            return "";
        }
    }

    private String addNewAccountForAdministrator(final AdministratorDto administratorDto) throws AppBaseException {
        accountEndpoint.addNewAdministrator(administratorDto);
        return "main";
    }
}
