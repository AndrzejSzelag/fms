package pl.szelag.manager;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractManager {

    protected static final Logger LOGGER = Logger.getGlobal();

    @Resource
    protected SessionContext sessionContext;

    private String transactionId;

    private boolean lastTransactionRollback;

    public void afterBegin() throws EJBException {
        transactionId = Long.toString(System.currentTimeMillis());
        LOGGER.log(Level.INFO, "Transaction ID={0} started in {1} by {2}.",
                new Object[]{transactionId, this.getClass().getName(), sessionContext.getCallerPrincipal().getName()});
    }

    public void beforeCompletion() throws EJBException {
        LOGGER.log(Level.INFO, "Transaction ID={0} before approval in {1} by {2}.",
                new Object[]{transactionId, this.getClass().getName(), sessionContext.getCallerPrincipal().getName()});
    }

    public void afterCompletion(boolean committed) throws EJBException {
        lastTransactionRollback = !committed;

        String txStatus = lastTransactionRollback ? "***ROLLBACK***" : "***COMMIT***";

        LOGGER.log(Level.INFO, "Transaction ID={0} completed in {1} through {3} by {2}.",
                new Object[]{transactionId, this.getClass().getName(), sessionContext.getCallerPrincipal().getName(), txStatus});
    }
}
