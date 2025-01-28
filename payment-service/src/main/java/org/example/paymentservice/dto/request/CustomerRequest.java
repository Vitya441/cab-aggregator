package org.example.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.example.paymentservice.util.ExceptionConstants;

@Builder
public record CustomerRequest(

        @NotBlank(message = ExceptionConstants.NAME_NOT_BLANK)
        @Size(max = 255, message = ExceptionConstants.NAME_SIZE)
        String name,

        @PositiveOrZero(message = ExceptionConstants.BALANCE_POSITIVE)
        Long balance
) {}