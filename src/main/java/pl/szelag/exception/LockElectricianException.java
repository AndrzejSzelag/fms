package pl.szelag.exception;

public final class LockElectricianException extends AppBaseException {

    private LockElectricianException() {
        super("OptimisticForceIncrement");
    }

    public static LockElectricianException optimisticLockExceptionForElectrician() {
        return new LockElectricianException();
    }
}
