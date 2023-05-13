package pl.szelag.endpoint;

import pl.szelag.converter.Converter;
import pl.szelag.dto.ElectricianDto;
import pl.szelag.interceptor.Logging;
import pl.szelag.manager.ElectricianManager;
import pl.szelag.model.Electrician;

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
@RolesAllowed({"Supervision"})
public class ElectricianEndpoint {

    @EJB
    private ElectricianManager electricianManager;

    public Collection<ElectricianDto> getSortedElectriciansByLoginNaturalSorting() {
        Collection<Electrician> electricians = electricianManager.findElectricians();
        return Converter.collectionElectriciansSortedByLogin(electricians);
    }
}
