package org.example.paymentservice.service;

import org.example.paymentservice.dto.request.CardRequest;
import org.example.paymentservice.dto.request.ChargeRequest;
import org.example.paymentservice.dto.request.CustomerChargeRequest;
import org.example.paymentservice.dto.request.CustomerRequest;
import org.example.paymentservice.dto.response.CardTokenResponse;
import org.example.paymentservice.dto.response.ChargeResponse;
import org.example.paymentservice.dto.response.CustomerResponse;

public interface PaymentService {

    CustomerResponse createCustomer(CustomerRequest customerRequest);

    CustomerResponse getCustomer(String customerId);

    ChargeResponse chargeFromCustomer(CustomerChargeRequest chargeRequest);

    CardTokenResponse generateToken(CardRequest cardRequest);

    String chargeCard(ChargeRequest chargeRequest);
}