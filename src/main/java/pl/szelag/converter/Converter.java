package pl.szelag.converter;

import com.google.common.collect.ImmutableList;
import pl.szelag.dto.*;
import pl.szelag.model.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public final class Converter {

    public static AccountDto convertToAccountDtoFromEntity(final Account account) {
        return null == account ? null : new AccountDto(
                account.getId(),
                account.getLogin(),
                account.getType(),
                account.getFirstName(),
                account.getLastName(),
                account.getPhone(),
                account.isActivated(),
                account.isConfirmed());
    }

    public static AccountDto convertAccountDtoFromEntity(final Account account) {
        
        if (account instanceof Electrician) {
            return convertToElectricianDtoFromEntity((Electrician) account);
        }
        
        if (account instanceof Supervision) {
            return convertToSupervisionDtoFromEntity((Supervision) account);
        }
        
        if (account instanceof Administrator) {
            return convertToAdministratorDtoFromEntity((Administrator) account);
        }
        
        return null;
    }

    public static ElectricianDto convertToElectricianDtoFromEntity(final Electrician electrician) {
        return null == electrician ? null : new ElectricianDto(
                electrician.getId(),
                electrician.getLogin(),
                electrician.getType(),
                electrician.getFirstName(),
                electrician.getLastName(),
                electrician.getPhone(),
                electrician.isActivated(),
                electrician.isConfirmed(),
                electrician.getDepartment());
    }

    private static SupervisionDto convertToSupervisionDtoFromEntity(final Supervision supervision) {
        return null == supervision ? null : new SupervisionDto(
                supervision.getId(),
                supervision.getLogin(),
                supervision.getType(),
                supervision.getFirstName(),
                supervision.getLastName(),
                supervision.getPhone(),
                supervision.isActivated(),
                supervision.isConfirmed(),
                supervision.getDepartment());
    }

    private static AccountDto convertToAdministratorDtoFromEntity(final Administrator administrator) {
        return null == administrator ? null : new AdministratorDto(
                administrator.getId(),
                administrator.getLogin(),
                administrator.getType(),
                administrator.getFirstName(),
                administrator.getLastName(),
                administrator.getPhone(),
                administrator.isActivated(),
                administrator.isConfirmed(),
                administrator.getAlarmPhone());
    }

    public static Collection<ElectricianDto> collectionElectriciansSortedByLogin(final Collection<Electrician> electricians) {
        return electricians.stream()
                .filter(Objects::nonNull)
                .map(Converter::convertToElectricianDtoFromEntity)
                .sorted(Comparator.comparing(ElectricianDto::getLogin))
                .collect(Collectors.collectingAndThen(toList(), ImmutableList::copyOf));
    }

    public static Collection<AccountDto> collectionAccountsSortedByUnconfirmedAndDeactivated(final Collection<Account> accounts) {
        return accounts.stream()
                .filter(Objects::nonNull)
                .map(Converter::convertToAccountDtoFromEntity)
                .sorted(Comparator.comparing(AccountDto::isConfirmed))
                .sorted(Comparator.comparing(AccountDto::isActivated))
                .collect(Collectors.collectingAndThen(toList(), ImmutableList::copyOf));
    }

    public static Collection<FaultDto> collectionFaultsSortedByReversedCreateTimestamp(final Collection<Fault> faults) {
        return faults.stream()
                .filter(Objects::nonNull)
                .map(Converter::convertToFaultDtoFromEntity)
                .sorted(Comparator.comparing(FaultDto::getCreateTimestamp).reversed())
                .collect(Collectors.collectingAndThen(toList(), ImmutableList::copyOf));
    }

    public static FaultDto convertToFaultDtoFromEntity(final Fault fault) {
        return null == fault ? null : new FaultDto(
                fault.getCreationTimestamp(),
                fault.getId(),
                fault.getFaultDescription(),
                convertToFaultStatusDtoFromEntity(fault.getStatus()),
                convertToStationDtoFromEntity(fault.getStation()),
                convertToElectricianDtoFromEntity(fault.getElectrician()),
                fault.getReported(),
                convertToSupervisionDtoFromEntity(fault.getSupervisionAccepted()),
                convertToSupervisionDtoFromEntity(fault.getSupervisionAssigned()),
                fault.getModificationTimestamp());
    }

    private static FaultDto.FaultStatusDto convertToFaultStatusDtoFromEntity(final Fault.FaultStatus faultStatus) {
        return null == faultStatus ? null : FaultDto.FaultStatusDto.valueOf(faultStatus.name());
    }

    public static StationDto convertToStationDtoFromEntity(final Station station) {
        return null == station ? null : new StationDto(
                station.getId(),
                station.getStationName());
    }

    public static Collection<StationDto> collectionStationsSortedByStationName(final Collection<Station> stations) {
        return stations.stream()
                .filter(Objects::nonNull)
                .map(Converter::convertToStationDtoFromEntity)
                .sorted(Comparator.comparing(StationDto::getStationName))
                .collect(Collectors.collectingAndThen(toList(), ImmutableList::copyOf));
    }
}
