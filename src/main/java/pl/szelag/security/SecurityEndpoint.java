package pl.szelag.security;

import pl.szelag.manager.AccountManager;
import pl.szelag.model.Account;

import javax.annotation.security.RunAs;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateful
@LocalBean
@TransactionAttribute(TransactionAttributeType.NEVER)
@RunAs("AUTHENTICATOR")
public class SecurityEndpoint {

    @Inject
    private AccountManager accountManager;

    public Account findAccountWithAuthConditions(String login, String password) {
        return accountManager.findUserDataWithAuthConditions(login, password);
    }
}
