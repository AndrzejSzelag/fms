package pl.szelag.manager;

import pl.szelag.exception.AppBaseException;
import pl.szelag.facade.StationFacade;
import pl.szelag.interceptor.Logging;
import pl.szelag.model.Station;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.interceptor.Interceptors;
import java.util.Collection;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(Logging.class)
@RolesAllowed("Supervision")
public class StationManager extends AbstractManager implements SessionSynchronization {

    @EJB
    private StationFacade stationFacade;

    public Collection<Station> findStations() {
        return stationFacade.findAll();
    }

    public void saveStation(Station station) throws AppBaseException {
        stationFacade.create(station);
    }
}
