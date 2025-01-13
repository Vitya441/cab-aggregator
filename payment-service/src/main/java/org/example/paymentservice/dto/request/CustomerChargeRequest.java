package org.example.paymentservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.example.paymentservice.util.ExceptionConstants;

@Builder
public record CustomerChargeRequest(

        @NotBlank(message = ExceptionConstants.CUSTOMER_ID_NOT_BLANK)
        String customerId,

        @NotBlank(message = ExceptionConstants.CURRENCY_NOT_BLANK)
        @Pattern(regexp = ExceptionConstants.CURRENCY_REGEX, message = ExceptionConstants.CURRENCY_REGEX_MESSAGE)
        String currency,

        @NotNull(message = ExceptionConstants.AMOUNT_NOT_NULL)
        @Min(value = 1, message = ExceptionConstants.AMOUNT_MIN)
        Long amount
) {}