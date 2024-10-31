package by.modsen.passengerservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@RequiredArgsConstructor
public class MessageUtils {

    private final MessageSource messageSource;

    public String getMessage(String key, Object... args) {
        String message = messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        return MessageFormat.format(message, args);
    }
}
