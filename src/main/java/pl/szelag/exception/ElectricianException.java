package pl.szelag.exception;

public class ElectricianException extends AppBaseException {

    public static final String NO_CONFIRMED_NO_ACTIVATED_ACCOUNT = "fault.cannot.be.assigned.to.electrician";

    protected ElectricianException() {
        super(ElectricianException.NO_CONFIRMED_NO_ACTIVATED_ACCOUNT);
    }

    protected ElectricianException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ElectricianException noConfirmedNoActivatedElectricianAccount() {
        return new ElectricianException();
    }
}
