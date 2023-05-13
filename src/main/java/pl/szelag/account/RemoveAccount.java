package pl.szelag.account;

import pl.szelag.dto.AccountDto;
import pl.szelag.endpoint.AccountEndpoint;
import pl.szelag.exception.AccountException;
import pl.szelag.exception.AppBaseException;
import pl.szelag.util.ContextUtil;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@ViewScoped
@Named(value = "removeAccount")
public class RemoveAccount implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    public String removeAccount(final AccountDto accountDto) throws AppBaseException {
        try {
            return getAccountForRemove(accountDto);
        } catch (AccountException ex) {
            Logger.getLogger(RemoveAccount.class.getName()).log(Level.SEVERE, "Report an exception in RemoveAccount.removeAccount(): ", ex);
            ContextUtil.emitInternationalizedMessage("form:infoForm", AccountException.ACCOUNT_NOT_FOUND);
            return "";
        }
    }

    private String getAccountForRemove(final AccountDto accountDto) throws AppBaseException {
        accountEndpoint.removeAccount(accountDto);
        return "accountList";
    }
}
