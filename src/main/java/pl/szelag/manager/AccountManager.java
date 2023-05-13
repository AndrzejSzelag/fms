package pl.szelag.manager;

import pl.szelag.exception.AccountException;
import pl.szelag.exception.AppBaseException;
import pl.szelag.facade.AccountFacade;
import pl.szelag.interceptor.Logging;
import pl.szelag.model.Account;
import pl.szelag.model.Administrator;
import pl.szelag.model.Electrician;
import pl.szelag.model.Supervision;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(Logging.class)
@RolesAllowed({"Administrator"})
public class AccountManager extends AbstractManager implements SessionSynchronization {

    @EJB
    private AccountFacade accountFacade;

    @ExcludeClassInterceptors
    @RolesAllowed("AUTHENTICATOR")
    public Account findUserDataWithAuthConditions(String login, String password) {
        return accountFacade.findLogin(login, password);
    }

    @RolesAllowed({"Administrator", "Supervision", "Electrician"})
    public Account findLogin(String login) throws AccountException {
        return accountFacade.findLoginForUserAuthenticate(login);
    }

    public Account findAccountId(Long id) throws AccountException {
        return accountFacade.find(id);
    }

    public List findAccounts() {
        return accountFacade.findAll();
    }

    public void confirmAccount(Account account, boolean isConfirmed) {
        accountFacade.setConfirmed(account, isConfirmed);
    }

    public void activateAccount(Account account, boolean isActivated) {
        accountFacade.setActive(account, isActivated);
    }

    public void saveNewAdmistrator(Administrator administrator) throws AppBaseException {
        accountFacade.create(administrator);
    }

    @PermitAll
    public void saveNewElectrician(Electrician electrician) throws AppBaseException {
        accountFacade.create(electrician);
    }

    public void saveNewSupervision(Supervision supervision) throws AppBaseException {
        accountFacade.create(supervision);
    }

    public void saveAccount(Account account) throws AppBaseException {
        accountFacade.edit(account);
    }

    public void removeAccount(Account account) throws AppBaseException {
        accountFacade.remove(account);
    }

    @RolesAllowed({"Administrator", "Electrician", "Supervision"})
    public void changeMyPassword(Account account, String newPassword) {
        accountFacade.changeMyPassword(account, newPassword);
    }
}
