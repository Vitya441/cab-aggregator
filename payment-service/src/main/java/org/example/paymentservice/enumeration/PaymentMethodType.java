package org.example.paymentservice.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethodType {
    VISA("pm_card_visa");

    private final String visa;
}