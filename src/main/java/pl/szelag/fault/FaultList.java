package pl.szelag.fault;

import lombok.Getter;
import pl.szelag.dto.FaultDto;
import pl.szelag.endpoint.FaultEndpoint;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@ViewScoped
@Named(value = "faultList")
public class FaultList implements Serializable {

    @EJB
    private FaultEndpoint faultEndpoint;

    @Getter
    private Collection<FaultDto> faultDtoList;

    @PostConstruct
    protected void getFaultList() {
        faultDtoList = faultEndpoint.getSortedFaultListByReversedCreateTimestamp();
    }
}
