package pl.szelag.account;

import lombok.Getter;
import lombok.Setter;
import pl.szelag.dto.SupervisionDto;
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
@Named(value = "createSupervision")
public class CreateSupervision implements Serializable {

    private static final String MESSAGE_ID_FORM = "form:infoForm";

    @EJB
    private AccountEndpoint accountEndpoint;

    @Getter
    private final SupervisionDto supervisionDto = new SupervisionDto();

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 8, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\._-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String passwordRepeat = "";

    public String createNewSupervision() throws AppBaseException {
        if (supervisionDto.getPassword().equals(passwordRepeat)) {
            return createNewAccountForSupervision(supervisionDto);
        } else {
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.PASSWORDS_NOT_MATCH);
            return "";
        }
    }

    private String createNewAccountForSupervision(final SupervisionDto supervisionDto) throws AppBaseException {
        try {
            return addNewAccountForSupervision(supervisionDto);
        } catch (AccountException ex) {
            Logger.getLogger(CreateSupervision.class.getName()).log(Level.SEVERE, "Report an exception in CreateSupervision.createNewAccountForSupervision(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.GIVEN_LOGIN_ALREADY_EXIST);
            return "";
        }
    }

    private String addNewAccountForSupervision(final SupervisionDto supervisionDto) throws AppBaseException {
        accountEndpoint.addNewSupervision(supervisionDto);
        return "main";
    }
}
