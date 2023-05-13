package pl.szelag.util;

import pl.szelag.endpoint.AccountEndpoint;
import pl.szelag.exception.AccountException;
import pl.szelag.model.Account;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
@Named(value = "accountSession")
public class AccountSession implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(AccountSession.class.getName());

    @EJB
    private AccountEndpoint accountEndpoint;

    @PostConstruct
    protected void startAccountSession() {
        LOGGER.log(Level.SEVERE, "Start session ID={0} by {1}", new Object[]{getSession(), getUserName()});
    }

    public String getSession() {
        return ContextUtil.getSessionID();
    }

    public String getUserName() {
        return ContextUtil.getUserName();
    }

    public String resetSession() {
        invalidateSession();
        LOGGER.log(Level.SEVERE, "{0} has ended the session.", getUserName());
        return "main";
    }

    private String invalidateSession() {
        return ContextUtil.invalidateSession();
    }

    public String getFirstAndLastName() {
        try {
            Account user = accountEndpoint.getLogin();
            return user.getFirstName() + " " + user.getLastName();
        } catch (AccountException ex) {
            LOGGER.log(Level.SEVERE, "Report an exception in AccountSession.getFirstAndLastName(): ", ex);
            ContextUtil.emitInternationalizedMessage("form:infoForm", AccountException.ACCOUNT_NOT_FOUND);
            resetSession();
            return "";
        }
    }
}
