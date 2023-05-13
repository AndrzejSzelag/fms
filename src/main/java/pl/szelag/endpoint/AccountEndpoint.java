package pl.szelag.endpoint;

import pl.szelag.converter.Converter;
import pl.szelag.dto.AccountDto;
import pl.szelag.dto.AdministratorDto;
import pl.szelag.dto.ElectricianDto;
import pl.szelag.dto.SupervisionDto;
import pl.szelag.exception.AccountException;
import pl.szelag.exception.AppBaseException;
import pl.szelag.interceptor.Logging;
import pl.szelag.manager.AccountManager;
import pl.szelag.model.Account;
import pl.szelag.model.Administrator;
import pl.szelag.model.Electrician;
import pl.szelag.model.Supervision;
import pl.szelag.security.HashGenerator;
import pl.szelag.util.ContextUtil;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Collection;

@Stateful
@Interceptors(Logging.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
@RolesAllowed({"Administrator"})
public class AccountEndpoint {

    @EJB
    private AccountManager accountManager;

    @Inject
    private HashGenerator hashGenerator;

    private Account account;

    @RolesAllowed({"Administrator", "Supervision", "Electrician"})
    public AccountDto getAccountData() throws AccountException {
        return Converter.convertAccountDtoFromEntity(getLogin());
    }

    @RolesAllowed({"Administrator", "Supervision", "Electrician"})
    public Account getLogin() throws AccountException {
        return accountManager.findLogin(ContextUtil.getUserName());
    }

    public AccountDto getAccountDetails(final AccountDto accountDto) throws AccountException {
        getAccountId(accountDto);
        return Converter.convertAccountDtoFromEntity(account);
    }

    private void getAccountId(final AccountDto accountDto) throws AccountException {
        account = accountManager.findAccountId(accountDto.getId());
    }

    public Collection<AccountDto> getSortedAccountsByReversedId() {
        Collection<Account> accounts = accountManager.findAccounts();
        return Converter.collectionAccountsSortedByUnconfirmedAndDeactivated(accounts);
    }

    public AccountDto getAccountToEdit(final AccountDto accountDto) throws AccountException {
        getAccountId(accountDto);
        return Converter.convertAccountDtoFromEntity(account);
    }

    public void setAccountToConfirm(AccountDto accountDto) throws AccountException {
        getAccountId(accountDto);
        accountManager.confirmAccount(account, true);
    }

    public void setAccountToActivate(AccountDto accountDto) throws AccountException {
        getAccountId(accountDto);
        accountManager.activateAccount(account, true);
    }

    public void setAccountToDeactivate(AccountDto accountDto) throws AccountException {
        getAccountId(accountDto);
        accountManager.activateAccount(account, false);
    }

    @RolesAllowed({"Administrator", "Supervision", "Electrician"})
    public void changeMyPassword(String oldPassword, String newPassword) throws AccountException {
        account = getLogin();
        validateIfAccountExist();
        String newHash = hashGenerator.generateHash(newPassword);
        
        if (!account.getPassword().equals(hashGenerator.generateHash(oldPassword))) {
            throw AccountException.givenInvalidOldPassword();
        }
        
        if (account.getPassword().equals(hashGenerator.generateHash(newPassword))) {
            throw AccountException.givenPasswordsMatch();
        }
        
        accountManager.changeMyPassword(account, newHash);
    }

    @RolesAllowed({"Administrator", "Supervision", "Electrician"})
    private void validateIfAccountExist() throws AccountException {
        if (account == null) {
            throw AccountException.accountNotFound();
        }
    }

    public void addNewAdministrator(AdministratorDto administratorDto) throws AppBaseException {
        Administrator newAdministrator = new Administrator();
        writeAccountFromDtoToEntity(administratorDto, newAdministrator);
        newAdministrator.setAlarmPhone(administratorDto.getAlarmPhone());
        accountManager.saveNewAdmistrator(newAdministrator);
    }

    public void writeAccountFromDtoToEntity(AccountDto accountDto, Account account) {
        account.setLogin(accountDto.getLogin());
        writeEditableAccountFromDtoToEntity(accountDto, account);
        account.setPassword(hashGenerator.generateHash(accountDto.getPassword()));
    }

    private void writeEditableAccountFromDtoToEntity(AccountDto accountDto, Account account) {
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setPhone(accountDto.getPhone());
        account.setActivated(accountDto.isActivated());
        account.setConfirmed(accountDto.isConfirmed());
    }

    @PermitAll
    public void addNewElectrician(ElectricianDto electricianDto) throws AppBaseException {
        Electrician newElectrician = new Electrician();
        writeAccountFromDtoToEntity(electricianDto, newElectrician);
        newElectrician.setDepartment(electricianDto.getDepartment());
        accountManager.saveNewElectrician(newElectrician);
    }

    public void addNewSupervision(SupervisionDto supervisionDto) throws AppBaseException {
        Supervision newSupervision = new Supervision();
        writeAccountFromDtoToEntity(supervisionDto, newSupervision);
        newSupervision.setDepartment(supervisionDto.getDepartment());
        accountManager.saveNewSupervision(newSupervision);
    }

    public void removeAccount(AccountDto accountDto) throws AppBaseException {
        
        if (accountDto.getLogin().equals(ContextUtil.getUserName())) {
            throw AccountException.youCannotDeletedYourAccount();
        }
        
        if (accountDto.isConfirmed()) {
            throw AccountException.accountNotRemove();
        }
        
        getAccountId(accountDto);
        
        accountManager.removeAccount(account);
    }

    public void resetPassword(AccountDto accountDto) throws AppBaseException {
        account.setPassword(hashGenerator.generateHash(accountDto.getPassword()));
        accountManager.saveAccount(account);
    }

    public void saveAdministratorAfterEdit(AccountDto administratorDto) throws AppBaseException {
        writeEditableAccountFromDtoToEntity(administratorDto, account);
        ((Administrator) account).setAlarmPhone(((AdministratorDto) administratorDto).getAlarmPhone());
        accountManager.saveAccount(account);
    }

    public void saveSupervisionAfterEdit(AccountDto supervisionDto) throws AppBaseException {
        writeEditableAccountFromDtoToEntity(supervisionDto, account);
        ((Supervision) account).setDepartment(((SupervisionDto) supervisionDto).getDepartment());
        accountManager.saveAccount(account);
    }

    public void saveElectricianAfterEdit(AccountDto electricianDto) throws AppBaseException {
        writeEditableAccountFromDtoToEntity(electricianDto, account);
        ((Electrician) account).setDepartment(((ElectricianDto) electricianDto).getDepartment());
        accountManager.saveAccount(account);
    }
}
