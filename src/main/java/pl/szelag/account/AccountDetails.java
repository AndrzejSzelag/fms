package pl.szelag.account;

import lombok.Getter;
import pl.szelag.dto.AccountDto;
import pl.szelag.endpoint.AccountEndpoint;
import pl.szelag.exception.AccountException;
import pl.szelag.util.ContextUtil;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
@Named(value = "accountDetails")
public class AccountDetails extends AccountType implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    private AccountDto accountDto;

    @Getter
    private AccountDto accountDtoDetails;

    public String downloadAccountDetails(final AccountDto accountDto) {
        try {
            return getAccountDetails(accountDto);
        } catch (AccountException ex) {
            Logger.getLogger(AccountDetails.class.getName()).log(Level.SEVERE, "Report an exception in AccountDetails.downloadAccountDetails(): ", ex);
            ContextUtil.emitInternationalizedMessage("form:infoForm", AccountException.ACCOUNT_NOT_FOUND);
            return "";
        }
    }

    private String getAccountDetails(final AccountDto accountDto) throws AccountException {
        accountDtoDetails = accountEndpoint.getAccountDetails(accountDto);
        this.accountDto = accountDtoDetails;
        return "accountDetails";
    }

    public boolean isAdministrator() {
        return AccountType.isAdministrator(accountDto);
    }

    public boolean isSupervision() {
        return AccountType.isSupervision(accountDto);
    }

    public boolean isElectrician() {
        return AccountType.isElectrician(accountDto);
    }
}
