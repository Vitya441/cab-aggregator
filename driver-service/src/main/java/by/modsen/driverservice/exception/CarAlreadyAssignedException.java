package by.modsen.driverservice.exception;

public class CarAlreadyAssignedException extends BasicException {

    public CarAlreadyAssignedException(String message, Object... args) {
        super(message, args);
    }
}
