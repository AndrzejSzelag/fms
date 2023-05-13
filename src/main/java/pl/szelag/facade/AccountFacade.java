package pl.szelag.facade;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.szelag.exception.AccountException;
import pl.szelag.exception.AppBaseException;
import pl.szelag.exception.AppBasePersistenceException;
import pl.szelag.interceptor.Logging;
import pl.szelag.model.Account;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.szelag.model.Account_;

@Stateless
@Interceptors(Logging.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@RolesAllowed({"Administrator"})
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(name = "FMS_PU", unitName = "FMS_PU", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public AccountFacade() {
        super(Account.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @PermitAll
    @ExcludeClassInterceptors
    public void create(Account account) throws AppBaseException {
        try {
            super.create(account);
        } catch (PersistenceException pEx) {
            if (pEx.getCause() instanceof DatabaseException && pEx.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw AccountException.dBCheckConstraintKey(pEx);
            } else {
                throw AppBasePersistenceException.createPersistenceException();
            }
        }
    }

    @ExcludeClassInterceptors
    @RolesAllowed({"Administrator", "Supervision", "Electrician"})
    public Account findLoginForUserAuthenticate(String login) throws AccountException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get("login"), login));
        TypedQuery<Account> tq = em.createQuery(query);
        try {
            return tq.getSingleResult();
        } catch (NoResultException nre) {
            throw AccountException.accountNotFound();
        }
    }

    @Override
    @ExcludeClassInterceptors
    public Account find(Object id) throws AccountException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get("id"), id));
        TypedQuery<Account> tq = em.createQuery(query);
        try {
            return tq.getSingleResult();
        } catch (NoResultException nre) {
            throw AccountException.accountNotFound();
        }
    }

    @ExcludeClassInterceptors
    @RolesAllowed("AUTHENTICATOR")
    public Account findLogin(String login, String hashPassword) {
        if (null == login || null == hashPassword || login.isEmpty() || hashPassword.isEmpty()) return null;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        Predicate criteria = cb.conjunction();
        criteria = cb.and(criteria, cb.equal(from.get(Account_.login), login));
        criteria = cb.and(criteria, cb.equal(from.get(Account_.password), hashPassword));
        criteria = cb.and(criteria, cb.isTrue(from.get(Account_.isActivated)));
        criteria = cb.and(criteria, cb.isTrue(from.get(Account_.isConfirmed)));
        query = query.select(from);
        query = query.where(criteria);
        TypedQuery<Account> tq = em.createQuery(query);
        try {
            return tq.getSingleResult();
        } catch (NoResultException | NonUniqueResultException nre) {
            Logger.getLogger(AccountFacade.class.getName()).log(Level.SEVERE, "Authentication for login: {0} failed with: {1}", new Object[]{login, nre});
        }
        return null;
    }

    @Override
    @ExcludeClassInterceptors
    public void edit(Account account) throws AppBaseException {
        try {
            super.edit(account);
        } catch (OptimisticLockException oleEx) {
            throw AccountException.optimisticLockAccount(oleEx);
        } catch (PersistenceException pEx) {
            if (pEx.getCause() instanceof DatabaseException && pEx.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw AccountException.dBCheckConstraintKey(pEx);
            } else {
                throw AppBasePersistenceException.createPersistenceException();
            }
        } 
    }

    @Override
    @ExcludeClassInterceptors
    public void remove(Account account) throws AppBaseException {
        try {
            super.remove(account);
        } catch (PersistenceException pEx) {
            if (pEx.getCause() instanceof DatabaseException
                    && pEx.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw AccountException.accountNotRemove();
            } else {
                throw pEx;
            }
        }
    }

    @ExcludeClassInterceptors
    @RolesAllowed({"Administrator", "Electrician", "Supervision"})
    public void changeMyPassword(Account account, String newPassword) {
        em.find(account.getClass(), account.getId()).setPassword(newPassword);
    }

    @ExcludeClassInterceptors
    public void setConfirmed(Account account, boolean isConfirmed) {
        em.find(account.getClass(), account.getId()).setConfirmed(isConfirmed);
    }

    @ExcludeClassInterceptors
    public void setActive(Account account, boolean isActivated) {
        em.find(account.getClass(), account.getId()).setActivated(isActivated);
    }
}
