package pl.szelag.station;

import lombok.Getter;
import pl.szelag.dto.StationDto;
import pl.szelag.endpoint.StationEndpoint;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@ViewScoped
@Named(value = "stationList")
public class StationList implements Serializable {

    @EJB
    private StationEndpoint stationEndpoint;

    @Getter
    private Collection<StationDto> stationDtoList;

    @PostConstruct
    protected void getFaultList() {
        stationDtoList = stationEndpoint.getSortedStationsByNaturalSorting();
    }
}
