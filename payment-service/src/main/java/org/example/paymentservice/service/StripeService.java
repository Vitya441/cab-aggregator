package org.example.paymentservice.service;

import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import org.example.paymentservice.dto.request.CardRequest;
import org.example.paymentservice.dto.request.ChargeRequest;
import org.example.paymentservice.dto.request.CustomerChargeRequest;
import org.example.paymentservice.dto.request.CustomerRequest;

public interface StripeService {

    Customer createStripeCustomer(CustomerRequest customerRequest);

    void createPayment(String customerId);

    PaymentIntent confirmIntent(CustomerChargeRequest request, String customerId);

    PaymentIntent createIntent(CustomerChargeRequest request, String customerId);

    void changeBalance(String customerId, Long amount);

    Charge createCharge(ChargeRequest chargeRequest);

    Token createToken(CardRequest cardRequest);
}