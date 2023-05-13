package pl.szelag.exception;

public class StationException extends AppBaseException {

    public static final String GIVEN_STATION_ALREADY_EXIST = "error.db.constraint.unique.station";

    protected StationException(String message) {
        super(message);
    }

    protected StationException(Throwable cause) {
        super(StationException.GIVEN_STATION_ALREADY_EXIST, cause);
    }

    public static StationException dBCheckConstraintKey(Throwable cause) {
        return new StationException(cause);
    }

    public static StationException dBCheckConstraintKey() {
        return new StationException(GIVEN_STATION_ALREADY_EXIST);
    }
}
