package pl.szelag.manager;

import pl.szelag.facade.ElectricianFacade;
import pl.szelag.interceptor.Logging;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(Logging.class)
@RolesAllowed("Supervision")
public class ElectricianManager extends AbstractManager implements SessionSynchronization {

    @EJB
    private ElectricianFacade electricianFacade;

    public List findElectricians() {
        return electricianFacade.findAll();
    }
}
