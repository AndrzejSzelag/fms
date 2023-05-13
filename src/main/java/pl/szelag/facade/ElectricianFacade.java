package pl.szelag.facade;

import pl.szelag.interceptor.Logging;
import pl.szelag.model.Electrician;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import static javax.persistence.LockModeType.OPTIMISTIC_FORCE_INCREMENT;

@Stateless
@Interceptors(Logging.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@RolesAllowed("Supervision")
public class ElectricianFacade extends AbstractFacade<Electrician> {

    @PersistenceContext(name = "FMS_PU", unitName = "FMS_PU", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public ElectricianFacade() {
        super(Electrician.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void lockForElectrician(Electrician electrician) {
        em.lock(electrician, OPTIMISTIC_FORCE_INCREMENT);
    }
}
