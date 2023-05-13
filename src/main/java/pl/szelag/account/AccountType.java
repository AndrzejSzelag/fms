package pl.szelag.account;

import pl.szelag.dto.AccountDto;
import pl.szelag.dto.AdministratorDto;
import pl.szelag.dto.ElectricianDto;
import pl.szelag.dto.SupervisionDto;

public abstract class AccountType {

    public static boolean isAdministrator(final AccountDto accountDto) {
        return accountDto instanceof AdministratorDto;
    }

    public static boolean isSupervision(final AccountDto accountDto) {
        return accountDto instanceof SupervisionDto;
    }

    public static boolean isElectrician(final AccountDto accountDto) {
        return accountDto instanceof ElectricianDto;
    }
}
