package pl.szelag.facade;

import pl.szelag.exception.AccountException;
import pl.szelag.exception.AppBaseException;
import pl.szelag.interceptor.Logging;
import pl.szelag.model.Supervision;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
@Interceptors(Logging.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@RolesAllowed("Supervision")
public class SupervisionFacade extends AbstractFacade<Supervision> {

    @PersistenceContext(name = "FMS_PU", unitName = "FMS_PU", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public SupervisionFacade() {
        super(Supervision.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Supervision findSupervisionLogin(String login) throws AppBaseException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Supervision> query = cb.createQuery(Supervision.class);
        Root<Supervision> from = query.from(Supervision.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get("login"), login));
        TypedQuery<Supervision> tq = em.createQuery(query);
        try {
            return tq.getSingleResult();
        } catch (NoResultException nre) {
            throw AccountException.accountNotFound();
        }
    }
}
