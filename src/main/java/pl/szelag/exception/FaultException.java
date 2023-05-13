package pl.szelag.exception;

import javax.persistence.OptimisticLockException;

public class FaultException extends AppBaseException {

    public static final String OPTIMISTIC_LOCK_FAULT = "error.optimistic.lock.fault";
    public static final String ELECTRICIAN_ASSIGNED_TO_FAULT = "fault.same.electrician";
    public static final String FAULT_LIMIT = "error.fault.limit";
    public static final String FAULT_NOT_FOUND = "error.fault.not.found";
    public static final String STATUS_CHANGED_ALREADY = "error.status.changed.already";
    public static final String DB_CONSTRAINT = "error.fault.db.constraint.uniq";

    protected FaultException(String message) {
        super(message);
    }

    protected FaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FaultException electricianAssignedToFault() {
        return new FaultException(ELECTRICIAN_ASSIGNED_TO_FAULT);
    }

    public static FaultException faultsLimit() {
        return new FaultException(FAULT_LIMIT);
    }

    public static FaultException faultExceptionWithStatusChangedAlready() {
        return new FaultException(STATUS_CHANGED_ALREADY);
    }

    public static FaultException faultNotFound() {
        return new FaultException(FAULT_NOT_FOUND);
    }

    public static FaultException faultExceptionWithOptimisticLockKey(OptimisticLockException cause) {
        return new FaultException(OPTIMISTIC_LOCK_FAULT, cause);
    }

    public static FaultException dBCheckConstraintKey(Throwable cause) {
        return new FaultException(DB_CONSTRAINT, cause);
    }
}
