package pl.szelag.fault;

import pl.szelag.dto.FaultDto;
import pl.szelag.endpoint.FaultEndpoint;
import pl.szelag.exception.AppBaseException;
import pl.szelag.exception.FaultException;
import pl.szelag.util.ContextUtil;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
@Named(value = "editFault")
public class EditFault implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(EditFault.class.getName());
    private static final String MESSAGE_ID_FORM = "form:infoForm";

    @EJB
    private FaultEndpoint faultEndpoint;

    private FaultDto faultDto;

    public String editFaultDto(final FaultDto faultDto) throws AppBaseException {
        try {
            setFaultDtoToEdit(faultDto);
            return "editFault";
        } catch (FaultException ex) {
            LOGGER.log(Level.SEVERE, "Report an exception in EditFault.editFaultDto(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, FaultException.FAULT_NOT_FOUND);
            return "";
        }
    }

    public void setFaultDtoToEdit(FaultDto faultDto) throws AppBaseException {
        this.faultDto = faultEndpoint.getFaultToEdit(faultDto);
    }

    public String saveEditedFaultDto() throws AppBaseException {
        try {
            return addEditedFaultDto();
        } catch (FaultException ex) {
            LOGGER.log(Level.SEVERE, "Report an exception in EditFault.saveEditedFaultDto(): ", ex);
            ContextUtil.emitInternationalizedMessage(MESSAGE_ID_FORM, FaultException.OPTIMISTIC_LOCK_FAULT);
            return "";
        }
    }

    public String addEditedFaultDto() throws FaultException, AppBaseException {
        faultEndpoint.saveFaultAfterEdit(faultDto);
        return "faultList";
    }

    public FaultDto getFaultDto() {
        if (faultDto != null) {
            return faultDto;
        }
        return new FaultDto();
    }
}
