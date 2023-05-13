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
@Named(value = "supervisionFaultList")
public class SupervisionFaultList implements Serializable {

    @EJB
    private FaultEndpoint faultEndpoint;

    @Getter
    private Collection<FaultDto> supervisionAcceptedFaultDtoList;

    @Getter
    private Collection<FaultDto> supervisionAssignedFaultDtoList;

    @PostConstruct
    protected void getSupervisionFaultList() {
        supervisionAcceptedFaultDtoList = faultEndpoint.getSortedSupervisionAcceptedFaultListByReversedCreateTimestamp();
        supervisionAssignedFaultDtoList = faultEndpoint.getSortedSupervisionAssignedFaultListByReversedCreateTimestamp();
    }
}