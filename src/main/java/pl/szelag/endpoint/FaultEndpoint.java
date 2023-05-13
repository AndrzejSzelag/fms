package pl.szelag.endpoint;

import pl.szelag.converter.Converter;
import pl.szelag.dto.ElectricianDto;
import pl.szelag.dto.FaultDto;
import pl.szelag.exception.AppBaseException;
import pl.szelag.exception.FaultException;
import pl.szelag.interceptor.Logging;
import pl.szelag.manager.FaultManager;
import pl.szelag.model.Fault;
import pl.szelag.util.ContextUtil;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import java.util.Collection;

@Stateful
@Interceptors(Logging.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
@RolesAllowed("Supervision")
public class FaultEndpoint {

    @EJB
    private FaultManager faultManager;

    private Fault fault;

    public Collection<FaultDto> getSortedFaultListByReversedCreateTimestamp() {
        Collection<Fault> faultList = faultManager.findFaults();
        return Converter.collectionFaultsSortedByReversedCreateTimestamp(faultList);
    }

    @RolesAllowed("Electrician")
    public Collection<FaultDto> getSortedElectricianFaultListByReversedCreateTimestamp() {
        Collection<Fault> electricianFaultList = faultManager.findElectricianFaults(ContextUtil.getUserName());
        return Converter.collectionFaultsSortedByReversedCreateTimestamp(electricianFaultList);
    }

    public Collection<FaultDto> getSortedSupervisionAcceptedFaultListByReversedCreateTimestamp() {
        Collection<Fault> supervisionAcceptedFaultList = faultManager.findSupervisionAcceptedFaults(ContextUtil.getUserName());
        return Converter.collectionFaultsSortedByReversedCreateTimestamp(supervisionAcceptedFaultList);
    }

    public Collection<FaultDto> getSortedSupervisionAssignedFaultListByReversedCreateTimestamp() {
        Collection<Fault> supervisionAssignedFaultList = faultManager.findSupervisionAssignedFaults(ContextUtil.getUserName());
        return Converter.collectionFaultsSortedByReversedCreateTimestamp(supervisionAssignedFaultList);
    }

    public FaultDto getFaultToEdit(final FaultDto faultDto) throws FaultException {
        getFaultId(faultDto);
        return Converter.convertToFaultDtoFromEntity(fault);
    }

    private void getFaultId(final FaultDto faultDto) throws FaultException {
        fault = faultManager.findFaultId(faultDto.getId());
    }

    @RolesAllowed({"Electrician", "Supervision"})
    public void setFaultDtoToChange(FaultDto faultDto) throws FaultException {
        getFaultId(faultDto);
        validateIfFaultExist();
    }

    private void validateIfFaultExist() throws FaultException {
        if (fault == null) {
            throw FaultException.faultNotFound();
        }
    }

    @RolesAllowed({"Electrician", "Supervision"})
    public void setFaultStatusEnd(FaultDto faultDto) throws FaultException {
        setFaultDtoToChange(faultDto);
        faultManager.setFaultStatus(fault, "ENDED");
    }

    public void addNewFault(FaultDto faultDto, Long id) throws AppBaseException {
        Fault newFault = new Fault();
        newFault.setFaultDescription(faultDto.getFaultDescription());
        newFault.setReported(faultDto.getReported());
        faultManager.saveFault(newFault, id);
    }

    public void saveFaultAfterEdit(FaultDto faultDto) throws AppBaseException {
        writeFaultFromDtoToEntity(faultDto, fault);
        faultManager.saveFault(fault);
    }

    private void writeFaultFromDtoToEntity(FaultDto faultDto, Fault fault) {
        fault.setFaultDescription(faultDto.getFaultDescription());
        fault.setReported(faultDto.getReported());
    }

    public void assignFault(ElectricianDto electricianDto) throws AppBaseException {
        Long electricianId = electricianDto.getId();
        validateIfFaultExist();
        faultManager.assignElectrician(fault, electricianId);
    }
}
