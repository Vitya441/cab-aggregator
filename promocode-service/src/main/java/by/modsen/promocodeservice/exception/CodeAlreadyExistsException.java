package by.modsen.promocodeservice.exception;

public class CodeAlreadyExistsException extends BasicException {
    public CodeAlreadyExistsException(String message, Object... args) {
        super(message, args);
    }
}