package pl.szelag.account;

import lombok.Getter;
import lombok.Setter;
import pl.szelag.dto.AccountDto;
import pl.szelag.endpoint.AccountEndpoint;
import pl.szelag.exception.AccountException;
import pl.szelag.exception.AppBaseException;
import pl.szelag.util.ContextUtil;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
@Named(value = "resetPassword")
public class ResetPassword implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ResetPassword.class.getName());
    private static final String MESSAGE_ID_FORM = "form:infoForm";

    @EJB
    private AccountEndpoint accountEndpoint;

    private AccountDto accountDto;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 8, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\._-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String passwordRepeat = "";

    public String resetPasswordToAccount() throws AppBaseException {
        
        if (!passwordRepeat.equals(accountDto.getPassword())) {
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.PASSWORDS_NOT_MATCH);
            return "";
        }
        
        try {
            accountEndpoint.resetPassword(accountDto);
            return "accountList";
        } catch (AccountException ex) {
            LOGGER.log(Level.SEVERE, "Report an exception in ResetPassword.resetPasswordToAccount(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.OPTIMISTIC_LOCK_ACCOUNT);
            return "";
        }
    }

    public String resetPassword(final AccountDto account) throws AppBaseException {
        try {
            return resetPasswordForAccount(account);
        } catch (AccountException ex) {
            Logger.getLogger(ResetPassword.class.getName()).log(Level.SEVERE, "Report an exception in ResetPassword.resetPassword(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.ACCOUNT_NOT_FOUND);
            return "";
        }
    }

    private String resetPasswordForAccount(final AccountDto account) throws AppBaseException {
        try {
            setAccountDtoToResetPassword(account);
            return "resetPassword";
        } catch (AccountException ex) {
            LOGGER.log(Level.SEVERE, "Report an exception in ResetPassword.resetPasswordForAccount(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.ACCOUNT_NOT_FOUND);
            return "";
        }
    }

    public void setAccountDtoToResetPassword(final AccountDto accountDto) throws AppBaseException {
        this.accountDto = accountEndpoint.getAccountToEdit(accountDto);
    }

    public AccountDto getAccountDto() {
        if (accountDto != null) {
            return accountDto;
        }
        return new AccountDto();
    }
}
