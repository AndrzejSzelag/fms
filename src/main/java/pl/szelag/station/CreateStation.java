package pl.szelag.station;

import lombok.Getter;
import pl.szelag.dto.StationDto;
import pl.szelag.endpoint.StationEndpoint;
import pl.szelag.exception.AppBaseException;
import pl.szelag.exception.StationException;
import pl.szelag.util.ContextUtil;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@ViewScoped
@Named(value = "createStation")
public class CreateStation implements Serializable {

    @EJB
    private StationEndpoint stationEndpoint;

    @Getter
    private final StationDto stationDto = new StationDto();

    public String createStation() throws AppBaseException {
        try {
            return addNewStation(stationDto);
        } catch (StationException ex) {
            Logger.getLogger(CreateStation.class.getName()).log(Level.SEVERE, "Report an exception in CreateStation.createStation(): ", ex);
            ContextUtil.emitInternationalizedMessage("form:infoForm", StationException.GIVEN_STATION_ALREADY_EXIST);
            return "";
        }
    }

    public String addNewStation(final StationDto stationDto) throws AppBaseException {
        stationEndpoint.addNewStation(stationDto);
        return "main";
    }
}
