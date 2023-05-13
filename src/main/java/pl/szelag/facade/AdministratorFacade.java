package pl.szelag.facade;

import pl.szelag.interceptor.Logging;
import pl.szelag.model.Administrator;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateless
@Interceptors(Logging.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@RolesAllowed({"Administrator"})
public class AdministratorFacade extends AbstractFacade<Administrator> {

    @PersistenceContext(name = "FMS_PU", unitName = "FMS_PU", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public AdministratorFacade() { super(Administrator.class); }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
