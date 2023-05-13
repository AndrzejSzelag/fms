package pl.szelag.fault;

import lombok.Getter;
import pl.szelag.dto.ElectricianDto;
import pl.szelag.dto.FaultDto;
import pl.szelag.endpoint.ElectricianEndpoint;
import pl.szelag.endpoint.FaultEndpoint;
import pl.szelag.exception.AppBaseException;
import pl.szelag.exception.FaultException;
import pl.szelag.util.ContextUtil;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
@Named(value = "electricianList")
public class ElectricianList implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ElectricianList.class.getName());

    @EJB
    private ElectricianEndpoint electricianEndpoint;

    @EJB
    private FaultEndpoint faultEndpoint;

    @Getter
    private Collection<ElectricianDto> electriciansDto;

    @PostConstruct
    protected void getElectricianList() {
        electriciansDto = electricianEndpoint.getSortedElectriciansByLoginNaturalSorting();
    }

    public void editFaultDto(final FaultDto faultDto) throws AppBaseException, FaultException {
        try {
            setFaultDtoToChange(faultDto);
        } catch (FaultException ex) {
            LOGGER.log(Level.SEVERE, "Report an exception in ElectricianList.editFaultDto(): ", ex);
            ContextUtil.emitInternationalizedMessage(null, ex.getMessage());
        }
    }

    public void setFaultDtoToChange(FaultDto faultDto) throws AppBaseException {
        faultEndpoint.setFaultDtoToChange(faultDto);
    }

    public String assignFaultToElectrician(ElectricianDto electricianDto) {
        try {
            return assignFault(electricianDto);
        } catch (AppBaseException ex) {
            LOGGER.log(Level.SEVERE, "Report an exception in ElectricianList.assignFaultToElectrician(): ", ex);
            ContextUtil.emitInternationalizedMessage(null, ex.getMessage());
            return "";
        }
    }

    public String assignFault(ElectricianDto electricianDto) throws AppBaseException {
        faultEndpoint.assignFault(electricianDto);
        return "faultList";
    }
}
