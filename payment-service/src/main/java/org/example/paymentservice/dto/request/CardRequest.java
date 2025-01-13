package org.example.paymentservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.example.paymentservice.util.ExceptionConstants;

@Builder
public record CardRequest(

        @NotBlank(message = ExceptionConstants.CARD_NUMBER_NOT_BLANK)
        @Pattern(regexp = ExceptionConstants.CARD_NUMBER_REGEX, message = ExceptionConstants.CARD_NUMBER_REGEX_MESSAGE)
        String cardNumber,

        @NotNull(message = ExceptionConstants.CARD_EXPIRATION_MONTH)
        @Min(value = 1, message = ExceptionConstants.CARD_EXPIRATION_MONTH_SIZE_MIN)
        @Max(value = 12, message = ExceptionConstants.CARD_EXPIRATION_MONTH_SIZE_MAX)
        Long expMonth,

        @NotNull(message = ExceptionConstants.CARD_EXPIRATION_YEAR)
        Long expYear,

        @NotBlank(message = ExceptionConstants.CARD_CVC_NOT_BLANK)
        @Pattern(regexp = ExceptionConstants.CARD_CVC_REGEX, message = ExceptionConstants.CARD_CVC_REGEX_MESSAGE)
        String cvc
) {}
