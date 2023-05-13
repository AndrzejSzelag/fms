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
@Named(value = "activateAccount")
public class ActivateAccount implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    public String activateAccount(final AccountDto accountDto) {
        try {
            return getAccountToActivate(accountDto);
        } catch (AccountException ex) {
            Logger.getLogger(ActivateAccount.class.getName()).log(Level.SEVERE, "Report an exception in ActivateAccount.activateAccount(): ", ex);
            ContextUtil.emitInternationalizedMessage("form:infoForm", AccountException.ACCOUNT_NOT_FOUND);
            return "";
        }
    }

    private String getAccountToActivate(final AccountDto accountDto) throws AccountException {
        accountEndpoint.setAccountToActivate(accountDto);
        return "accountList";
    }
}
