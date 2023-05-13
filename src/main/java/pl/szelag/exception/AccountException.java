package pl.szelag.exception;

import javax.persistence.OptimisticLockException;

public class AccountException extends AppBaseException {

    public static final String GIVEN_LOGIN_ALREADY_EXIST = "error.db.constraint.unique.login";
    public static final String YOU_CANNOT_DELETE_YOUR_ACCOUNT = "error.you.cannot.delete.your.account";
    public static final String INVALID_OLD_PASSWORD = "invalid.old.password";
    public static final String PASSWORDS_MATCH = "error.passwords.match";
    public static final String PASSWORDS_NOT_MATCH = "passwords.not.match";
    public static final String ACCOUNT_NOT_FOUND = "error.account.not.found";
    public static final String ACCOUNT_NOT_REMOVE = "error.account.not.remove";
    public static final String OPTIMISTIC_LOCK_ACCOUNT = "error.optimistic.lock.account";

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AccountException dBCheckConstraintKey(Throwable cause) {
        return new AccountException(GIVEN_LOGIN_ALREADY_EXIST, cause);
    }

    public static AccountException dBCheckConstraintKey() {
        return new AccountException(GIVEN_LOGIN_ALREADY_EXIST);
    }

    public static AccountException youCannotDeletedYourAccount() {
        return new AccountException(YOU_CANNOT_DELETE_YOUR_ACCOUNT);
    }

    public static AccountException givenInvalidOldPassword() {
        return new AccountException(INVALID_OLD_PASSWORD);
    }

    public static AccountException givenPasswordsMatch() {
        return new AccountException(PASSWORDS_MATCH);
    }

    public static AccountException accountNotFound() {
        return new AccountException(ACCOUNT_NOT_FOUND);
    }

    public static AccountException accountNotRemove() {
        return new AccountException(ACCOUNT_NOT_REMOVE);
    }

    public static AccountException optimisticLockAccount(OptimisticLockException cause) {
        return new AccountException(OPTIMISTIC_LOCK_ACCOUNT, cause);
    }
}
