package pl.szelag.account;

import pl.szelag.dto.AccountDto;
import pl.szelag.endpoint.AccountEndpoint;
import pl.szelag.exception.AccountException;
import pl.szelag.exception.AppBaseException;
import pl.szelag.util.ContextUtil;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
@Named(value = "editAccount")
public class EditAccount extends AccountType implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(EditAccount.class.getName());
    private static final String MESSAGE_ID_FORM = "form:infoForm";
    private static final String ACCOUNT_LIST = "accountList";

    @EJB
    private AccountEndpoint accountEndpoint;

    private AccountDto accountDto;

    public String editAccountDto(final AccountDto accountDto) {
        try {
            setAccountDtoToEdit(accountDto);
            return "editAccount";
        } catch (AccountException ex) {
            LOGGER.log(Level.SEVERE, "Report an exception in EditAccount.editAccountDto(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.ACCOUNT_NOT_FOUND);
            return "";
        }
    }

    public void setAccountDtoToEdit(AccountDto accountDto) throws AccountException {
        this.accountDto = accountEndpoint.getAccountToEdit(accountDto);
    }

    public String saveEditedAdministratorDto() throws AppBaseException {
        try {
            return addEditedAdministratorDto();
        } catch (AccountException ex) {
            LOGGER.log(Level.SEVERE, "Report an exception in EditAccount.saveEditedAdministratorDto(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.OPTIMISTIC_LOCK_ACCOUNT);
            return "";
        }
    }

    public String addEditedAdministratorDto() throws AppBaseException {
        accountEndpoint.saveAdministratorAfterEdit(accountDto);
        return ACCOUNT_LIST;
    }

    public String saveEditedSupervisionDto() throws AppBaseException {
        try {
            return addEditedSupervisionDto();
        } catch (AccountException ex) {
            LOGGER.log(Level.SEVERE, "Report an exception in EditAccount.saveEditedSupervisionDto(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.OPTIMISTIC_LOCK_ACCOUNT);
            return "";
        }
    }

    public String addEditedSupervisionDto() throws AppBaseException {
        accountEndpoint.saveSupervisionAfterEdit(accountDto);
        return ACCOUNT_LIST;
    }

    public String saveEditedElectricianDto() throws AppBaseException {
        try {
            return addEditedElectricianDto();
        } catch (AccountException ex) {
            LOGGER.log(Level.SEVERE, "Report an exception in EditAccount.saveEditedElectricianDto(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, AccountException.OPTIMISTIC_LOCK_ACCOUNT);
            return "";
        }
    }

    public String addEditedElectricianDto() throws AppBaseException {
        accountEndpoint.saveElectricianAfterEdit(accountDto);
        return ACCOUNT_LIST;
    }

    public AccountDto getAccountDto() {
        if (accountDto != null) {
            return accountDto;
        }
        return new AccountDto();
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
