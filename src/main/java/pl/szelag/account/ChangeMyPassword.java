package pl.szelag.account;

import lombok.Getter;
import lombok.Setter;
import pl.szelag.endpoint.AccountEndpoint;
import pl.szelag.exception.AccountException;
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
@Named(value = "changeMyPassword")
public class ChangeMyPassword implements Serializable {

    private static final String MESSAGE_ID_FORM = "form:infoForm";

    @EJB
    private AccountEndpoint accountEndpoint;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 8, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\p{N}._-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String passwordOld = "";

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 8, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\p{N}._-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String passwordNew = "";

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 8, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\p{N}._-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String passwordRepeat = "";

    public String checkPasswords() {
        
        if (passwordOld.equals(passwordNew) || passwordOld.equals(passwordRepeat)) {
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.PASSWORDS_MATCH);
            return "";
        }
        
        if (!(passwordRepeat.equals(passwordNew))) {
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.PASSWORDS_NOT_MATCH);
            return "";
        }
        
        try {
            return changeMyPassword();
        } catch (AccountException ex) {
            Logger.getLogger(ChangeMyPassword.class.getName()).log(Level.SEVERE, "Report an exception in ChangeMyPassword.checkPasswords(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.INVALID_OLD_PASSWORD);
            return "";
        }
    }

    public String changeMyPassword() throws AccountException {
        accountEndpoint.changeMyPassword(passwordOld, passwordNew);
        return "success";
    }
}
