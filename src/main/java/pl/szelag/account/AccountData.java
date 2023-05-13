package pl.szelag.account;

import lombok.Getter;
import pl.szelag.dto.AccountDto;
import pl.szelag.endpoint.AccountEndpoint;
import pl.szelag.exception.AccountException;
import pl.szelag.util.ContextUtil;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@ViewScoped
@Named(value = "accountData")
public class AccountData extends AccountType implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    private AccountDto accountDto;

    @Getter
    private AccountDto accountDtoData;

    @PostConstruct
    protected void downloadAccountData() {
        try {
            setAccountData();
        } catch (AccountException ex) {
            Logger.getLogger(AccountData.class.getName()).log(Level.SEVERE, "Report an exception in AccountData.downloadAccountData(): ", ex);
            ContextUtil.invalidateSession();
        }
    }

    private void setAccountData() throws AccountException {
        accountDtoData = accountEndpoint.getAccountData();
        this.accountDto = accountDtoData;
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
