package org.example.paymentservice.dto.response;

public record ErrorResponse(
        int statusCode,
        String message
) {}