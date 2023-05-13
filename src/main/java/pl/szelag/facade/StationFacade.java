package pl.szelag.facade;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.szelag.exception.AppBaseException;
import pl.szelag.exception.AppBasePersistenceException;
import pl.szelag.exception.StationException;
import pl.szelag.interceptor.Logging;
import pl.szelag.model.Station;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Stateless
@Interceptors(Logging.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@RolesAllowed("Supervision")
public class StationFacade extends AbstractFacade<Station> {

    @PersistenceContext(name = "FMS_PU", unitName = "FMS_PU", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public StationFacade() {
        super(Station.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List findAll() {
        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Station.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public void create(Station station) throws AppBaseException {
        try {
            super.create(station);
        } catch (PersistenceException pEx) {
            if (pEx.getCause() instanceof DatabaseException && pEx.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw StationException.dBCheckConstraintKey(pEx);
            } else {
                throw AppBasePersistenceException.createPersistenceException();
            }
        }
    }
}
