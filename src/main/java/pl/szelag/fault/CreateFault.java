package pl.szelag.fault;

import lombok.Getter;
import lombok.Setter;
import pl.szelag.dto.FaultDto;
import pl.szelag.dto.StationDto;
import pl.szelag.endpoint.FaultEndpoint;
import pl.szelag.endpoint.StationEndpoint;
import pl.szelag.exception.AppBaseException;
import pl.szelag.exception.FaultException;
import pl.szelag.util.ContextUtil;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@ViewScoped
@Named(value = "createFault")
public class CreateFault implements Serializable {

    @EJB
    private FaultEndpoint faultEndpoint;

    @EJB
    private StationEndpoint stationEndpoint;

    @Getter
    private Collection<StationDto> stationsDto;

    @Getter
    @Setter
    private long stationId;

    @Getter
    private final FaultDto faultDto = new FaultDto();

    @PostConstruct
    protected void getStationList() {
        stationsDto = stationEndpoint.getSortedStationsByNaturalSorting();
    }

    public String createFault() throws FaultException, AppBaseException {
        try {
            return addNewFault(faultDto);
        } catch (FaultException ex) {
            Logger.getLogger(CreateFault.class.getName()).log(Level.SEVERE, "Report an exception in CreateFault.createFault(): ", ex);
            ContextUtil.emitInternationalizedMessage(null, ex.getMessage());
            return "";
        }
    }

    public String addNewFault(final FaultDto faultDto) throws AppBaseException {
        faultEndpoint.addNewFault(faultDto, stationId);
        return "main";
    }
}
