package pl.szelag.account;

import pl.szelag.dto.AccountDto;
import pl.szelag.endpoint.AccountEndpoint;
import pl.szelag.exception.AccountException;
import pl.szelag.util.ContextUtil;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@ViewScoped
@Named(value = "confirmAccount")
public class ConfirmAccount implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    public String confirmAccount(final AccountDto accountDto) {
        try {
            return getAccountToConfirm(accountDto);
        } catch (AccountException ex) {
            Logger.getLogger(ConfirmAccount.class.getName()).log(Level.SEVERE, "Report an exception in ConfirmAccount.confirmAccount(): ", ex);
            ContextUtil.emitInternationalizedMessage("form:infoForm", AccountException.ACCOUNT_NOT_FOUND);
            return "";
        }
    }

    private String getAccountToConfirm(final AccountDto accountDto) throws AccountException {
        accountEndpoint.setAccountToConfirm(accountDto);
        return "accountList";
    }
}
