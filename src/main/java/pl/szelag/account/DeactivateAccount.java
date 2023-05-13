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
@Named(value = "deactivateAccount")
public class DeactivateAccount implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    public String deactivateAccount(final AccountDto accountDto) {
        try {
            return getAccountToDeactivate(accountDto);
        } catch (AccountException ex) {
            Logger.getLogger(DeactivateAccount.class.getName()).log(Level.SEVERE, "Report an exception in DeactivateAccount.deactivateAccount(): ", ex);
            ContextUtil.emitInternationalizedMessage("form:infoForm", AccountException.ACCOUNT_NOT_FOUND);
            return "";
        }
    }

    private String getAccountToDeactivate(final AccountDto accountDto) throws AccountException {
        accountEndpoint.setAccountToDeactivate(accountDto);
        return "accountList";
    }
}
