package pl.szelag.fault;

import pl.szelag.dto.FaultDto;
import pl.szelag.exception.AppBaseException;
import pl.szelag.exception.FaultException;
import pl.szelag.util.ContextUtil;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@ViewScoped
@Named(value = "assignElectrician")
public class AssignElectrician implements Serializable {

    @Inject
    private ElectricianList electricianList;

    public String assignElectricianToFault(final FaultDto faultDto) throws AppBaseException, FaultException {
        try {
            return getElectricianToAssignToFault(faultDto);
        } catch (FaultException ex) {
            Logger.getLogger(AssignElectrician.class.getName()).log(Level.SEVERE, "Report an exception in AssignElectrician.assignElectricianToFault(): ", ex);
            ContextUtil.emitInternationalizedMessage(null, ex.getMessage());
            return "";
        }
    }

    public String getElectricianToAssignToFault(final FaultDto faultDto) throws AppBaseException {
        electricianList.editFaultDto(faultDto);
        return "electricianList";
    }
}
