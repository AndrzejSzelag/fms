package pl.szelag.facade;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.szelag.exception.AppBaseException;
import pl.szelag.exception.AppBasePersistenceException;
import pl.szelag.exception.FaultException;
import pl.szelag.interceptor.Logging;
import pl.szelag.model.Electrician;
import pl.szelag.model.Fault;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.List;

@Stateless
@LocalBean
@Interceptors(Logging.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@RolesAllowed("Supervision")
public class FaultFacade extends AbstractFacade<Fault> {

    private static final String LOGIN = "login";

    @PersistenceContext(name = "FMS_PU", unitName = "FMS_PU", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public FaultFacade() {
        super(Fault.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Fault fault) throws AppBaseException {
        try {
            super.create(fault);
        } catch (PersistenceException pe) {
            throw AppBasePersistenceException.createPersistenceException();
        }
    }

    @Override
    public void edit(Fault fault) throws AppBaseException {
        try {
            super.edit(fault);
        } catch (OptimisticLockException ole) {
            throw FaultException.faultExceptionWithOptimisticLockKey(ole);
        } catch (PersistenceException pEx) {
            if (pEx.getCause() instanceof DatabaseException && pEx.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw FaultException.dBCheckConstraintKey(pEx);
            } else {
                throw pEx;
                //throw AppBasePersistenceException.createPersistenceException();
            }
        }
    }

    @RolesAllowed({"Electrician", "Supervision"})
    @Override
    public Fault find(Object id) throws FaultException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Fault> query = cb.createQuery(Fault.class);
        Root<Fault> from = query.from(Fault.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get("id"), id));
        TypedQuery<Fault> tq = em.createQuery(query);
        try {
            return tq.getSingleResult();
        } catch (NoResultException nre) {
            throw FaultException.faultNotFound();
        }
    }

    @Override
    public List findAll() {
        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Fault.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @RolesAllowed("Electrician")
    public Collection<Fault> findElectricianFaults(String login) {
        TypedQuery<Fault> tq = getEntityManager().createNamedQuery("Fault.findElectrician", Fault.class);
        tq.setParameter(LOGIN, login);
        return tq.getResultList();
    }

    public Collection<Fault> findSupervisionAcceptedFaults(String login) {
        TypedQuery<Fault> tq = getEntityManager().createNamedQuery("Fault.findSupervisionAccepted", Fault.class);
        tq.setParameter(LOGIN, login);
        return tq.getResultList();
    }

    public Collection<Fault> findSupervisionAssignedFaults(String login) {
        TypedQuery<Fault> tq = getEntityManager().createNamedQuery("Fault.findSupervisionAssigned", Fault.class);
        tq.setParameter(LOGIN, login);
        return tq.getResultList();
    }

    public int countOfElectrician(Electrician electrician) {
        Query q = getEntityManager().createNamedQuery("Fault.countOfElectrician");
        q.setParameter("electrician", electrician);
        q.setParameter("status", Fault.FaultStatus.ASSIGNED);
        return Integer.parseInt(q.getSingleResult().toString());
    }

    @RolesAllowed({"Electrician", "Supervision"})
    public void setStatus(Fault fault, String name) throws FaultException {
        if (!fault.getStatus().name().equals(name)) {
            em.find(fault.getClass(), fault.getId()).setStatus(Fault.FaultStatus.valueOf(name));
        } else {
            throw FaultException.faultExceptionWithStatusChangedAlready();
        }
    }
}
