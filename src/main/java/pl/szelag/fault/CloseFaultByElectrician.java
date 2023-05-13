package pl.szelag.fault;

import pl.szelag.dto.FaultDto;
import pl.szelag.endpoint.FaultEndpoint;
import pl.szelag.exception.FaultException;
import pl.szelag.util.ContextUtil;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@ViewScoped
@Named(value = "closeFaultByElectrician")
public class CloseFaultByElectrician implements Serializable {

    @EJB
    private FaultEndpoint faultEndpoint;

    public String closeFaultByElectrician(final FaultDto faultDto) {
        try {
            return getFaultToCloseByElectrician(faultDto);
        } catch (FaultException ex) {
            Logger.getLogger(CloseFaultByElectrician.class.getName()).log(Level.SEVERE, "Report an exception in CloseFaultByElectrician.closeFaultByElectrician(): ", ex);
            ContextUtil.emitInternationalizedMessage("form:infoForm", FaultException.STATUS_CHANGED_ALREADY);
            return "";
        }
    }

    public String getFaultToCloseByElectrician(final FaultDto faultDto) throws FaultException {
        faultEndpoint.setFaultStatusEnd(faultDto);
        return "electricianFaultList";
    }
}
