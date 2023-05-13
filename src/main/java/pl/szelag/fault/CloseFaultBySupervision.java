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
@Named(value = "closeFaultBySupervision")
public class CloseFaultBySupervision implements Serializable {

    @EJB
    private FaultEndpoint faultEndpoint;

    public String closeFaultBySupervision(final FaultDto faultDto) {
        try {
            return getFaultToCloseBySupervision(faultDto);
        } catch (FaultException ex) {
            Logger.getLogger(CloseFaultBySupervision.class.getName()).log(Level.SEVERE, "Report an exception in CloseFaultBySupervision.closeFaultBySupervision(): ", ex);
            ContextUtil.emitInternationalizedMessage("form:infoForm", FaultException.STATUS_CHANGED_ALREADY);
            return "";
        }
    }

    public String getFaultToCloseBySupervision(final FaultDto faultDto) throws FaultException {
        faultEndpoint.setFaultStatusEnd(faultDto);
        return "faultList";
    }
}
