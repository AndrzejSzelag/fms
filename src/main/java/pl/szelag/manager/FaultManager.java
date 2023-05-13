package pl.szelag.manager;

import pl.szelag.exception.AppBaseException;
import pl.szelag.exception.ElectricianException;
import pl.szelag.exception.FaultException;
import pl.szelag.exception.LockElectricianException;
import pl.szelag.facade.ElectricianFacade;
import pl.szelag.facade.FaultFacade;
import pl.szelag.facade.StationFacade;
import pl.szelag.facade.SupervisionFacade;
import pl.szelag.interceptor.Logging;
import pl.szelag.model.Electrician;
import pl.szelag.model.Fault;
import pl.szelag.model.Station;
import pl.szelag.model.Supervision;
import pl.szelag.util.ContextUtil;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.interceptor.Interceptors;
import java.util.Collection;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(Logging.class)
@RolesAllowed("Supervision")
public class FaultManager extends AbstractManager {

    @EJB
    private FaultFacade faultFacade;

    @EJB
    private StationFacade stationFacade;

    @EJB
    private ElectricianFacade electricianFacade;

    @EJB
    private SupervisionFacade supervisionFacade;

    @Resource(name = "faultLimit")
    private int faultLimit;

    @RolesAllowed({"Electrician", "Supervision"})
    public Fault findFaultId(Long id) throws FaultException {
        return faultFacade.find(id);
    }

    public List findFaults() {
        return faultFacade.findAll();
    }

    @RolesAllowed("Electrician")
    public Collection<Fault> findElectricianFaults(String login) {
        return faultFacade.findElectricianFaults(login);
    }

    public Collection<Fault> findSupervisionAcceptedFaults(String login) {
        return faultFacade.findSupervisionAcceptedFaults(login);
    }

    public Collection<Fault> findSupervisionAssignedFaults(String login) {
        return faultFacade.findSupervisionAssignedFaults(login);
    }

    @RolesAllowed({"Electrician", "Supervision"})
    public void setFaultStatus(Fault fault, String status) throws FaultException {
        faultFacade.setStatus(fault, status);
    }

    private int countOfElectrician(Electrician electrician) {
        return faultFacade.countOfElectrician(electrician);
    }

    public void saveFault(Fault fault, Long idStation) throws AppBaseException {
        Station station = stationFacade.find(idStation);
        fault.setStation(station);
        String supervisionLogin = ContextUtil.getUserName();
        Supervision supervision = supervisionFacade.findSupervisionLogin(supervisionLogin);
        fault.setSupervisionAccepted(supervision);
        faultFacade.create(fault);
    }

    public void saveFault(Fault fault) throws AppBaseException {
        faultFacade.edit(fault);
    }

    public void assignElectrician(Fault fault, Long id) throws AppBaseException {

        var electrician = electricianFacade.find(id);
        int electricianFaultsNumber = countOfElectrician(electrician);

        if (electrician == null) {
            throw ElectricianException.noConfirmedNoActivatedElectricianAccount();
        }

        if (!electrician.isConfirmed() || !electrician.isActivated()) {
            throw ElectricianException.noConfirmedNoActivatedElectricianAccount();
        }

        if (fault.getStatus() == Fault.FaultStatus.ENDED) {
            throw FaultException.faultExceptionWithStatusChangedAlready();
        }

        if (fault.getElectrician() != null && fault.getElectrician().equals(electrician)) {
            throw FaultException.electricianAssignedToFault();
        }

        if (electricianFaultsNumber < faultLimit) {
            try {
                electricianFacade.lockForElectrician(electrician);
                String supervisionLogin = ContextUtil.getUserName();
                Supervision supervision = supervisionFacade.findSupervisionLogin(supervisionLogin);
                fault.setSupervisionAssigned(supervision);
                fault.setElectrician(electrician);
                fault.setStatus(Fault.FaultStatus.ASSIGNED);
            } catch (EJBTransactionRolledbackException tre) {
                throw LockElectricianException.optimisticLockExceptionForElectrician();
            }
        } else {
            throw FaultException.faultsLimit();
        }
        faultFacade.edit(fault);
    }
}
