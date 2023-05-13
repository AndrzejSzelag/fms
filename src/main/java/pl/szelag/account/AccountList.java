package pl.szelag.account;

import lombok.Getter;
import pl.szelag.dto.AccountDto;
import pl.szelag.endpoint.AccountEndpoint;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@ViewScoped
@Named(value = "accountList")
public class AccountList implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    @Getter
    private Collection<AccountDto> accountDtoList;

    @PostConstruct
    protected void getAccountList() {
        accountDtoList = accountEndpoint.getSortedAccountsByReversedId();
    }
}
