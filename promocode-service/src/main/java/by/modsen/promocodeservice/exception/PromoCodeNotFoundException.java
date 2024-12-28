package by.modsen.promocodeservice.exception;

public class PromoCodeNotFoundException extends BasicException {
    public PromoCodeNotFoundException(String message, Object... args) {
        super(message, args);
    }
}
