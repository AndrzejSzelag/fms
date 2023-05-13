package pl.szelag.endpoint;

import pl.szelag.converter.Converter;
import pl.szelag.dto.StationDto;
import pl.szelag.exception.AppBaseException;
import pl.szelag.interceptor.Logging;
import pl.szelag.manager.StationManager;
import pl.szelag.model.Station;

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
public class StationEndpoint {

    @EJB
    private StationManager stationManager;

    public Collection<StationDto> getSortedStationsByNaturalSorting() {
        Collection<Station> stations = stationManager.findStations();
        return Converter.collectionStationsSortedByStationName(stations);
    }

    public void addNewStation(StationDto stationDto) throws AppBaseException {
        Station newStation = new Station();
        newStation.setStationName(stationDto.getStationName());
        stationManager.saveStation(newStation);
    }
}