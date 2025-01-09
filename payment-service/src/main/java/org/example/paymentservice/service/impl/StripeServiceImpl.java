package org.example.paymentservice.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentConfirmParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.paymentservice.dto.request.CardRequest;
import org.example.paymentservice.dto.request.ChargeRequest;
import org.example.paymentservice.dto.request.CustomerChargeRequest;
import org.example.paymentservice.dto.request.CustomerRequest;
import org.example.paymentservice.enumeration.PaymentMethodType;
import org.example.paymentservice.exception.BalanceException;
import org.example.paymentservice.exception.CreateCustomerException;
import org.example.paymentservice.exception.CreatePaymentException;
import org.example.paymentservice.exception.CreateTokenException;
import org.example.paymentservice.exception.NotFoundException;
import org.example.paymentservice.exception.PaymentException;
import org.example.paymentservice.service.StripeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeServiceImpl implements StripeService {

    @Value("${app.stripe.public_key}")
    private String publicKey;
    @Value("${app.stripe.secret_key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public Customer createStripeCustomer(CustomerRequest customerRequest) {
        CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                .setName(customerRequest.name())
                .setBalance(customerRequest.balance())
                .build();
        try {
            return Customer.create(customerCreateParams);
        } catch (StripeException ex) {
            throw new CreateCustomerException(ex.getMessage());
        }
    }

    @Override
    public void createPayment(String customerId) {
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("token", "tok_visa");

        Map<String, Object> paymentParams = new HashMap<>();
        paymentParams.put("type", "card");
        paymentParams.put("card", cardParams);

        try {
            PaymentMethod paymentMethod = PaymentMethod.create(paymentParams);
            paymentMethod.attach(Map.of("customer", customerId));
        } catch (StripeException ex) {
            throw new CreatePaymentException(ex.getMessage());
        }
    }

    @Override
    public PaymentIntent confirmIntent(CustomerChargeRequest request, String customerId) {
        PaymentIntent intent = createIntent(request, customerId);
        PaymentIntentConfirmParams params =
                PaymentIntentConfirmParams.builder()
                        .setPaymentMethod(PaymentMethodType.VISA.getVisa())
                        .build();
        try {
            return intent.confirm(params);
        } catch (StripeException ex) {
            throw new PaymentException(ex.getMessage());
        }
    }

    @Override
    public PaymentIntent createIntent(CustomerChargeRequest request, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", request.amount() * 100);
        params.put("currency", request.currency());
        params.put("customer", customerId);
        params.put("automatic_payment_methods", Map.of(
                "enabled", true,
                "allow_redirects", "never"
        ));
        try {
            return PaymentIntent.create(params);
        } catch (StripeException ex) {
            throw new PaymentException(ex.getMessage());
        }
    }

    @Override
    public void changeBalance(String customerId, Long amount) {
        Customer customer = retrieveCustomer(customerId);
        CustomerUpdateParams customerUpdateParams =
                CustomerUpdateParams.builder()
                        .setBalance(customer.getBalance() - amount).build();
        updateCustomer(customerUpdateParams, customer);
    }

    @Override
    public Charge createCharge(ChargeRequest chargeRequest) {
        Map<String, Object> chargeParams = createChargeParams(chargeRequest);
        try {
            return Charge.create(chargeParams);
        } catch (StripeException ex) {
            throw new PaymentException(ex.getMessage());
        }
    }

    @Override
    public Token createToken(CardRequest cardRequest) {
        Map<String, Object> cardParams = createCardParams(cardRequest);
        RequestOptions requestOptions = RequestOptions.builder()
                .setApiKey(publicKey)
                .build();
        try {
            return Token.create(Map.of("card", cardParams), requestOptions);
        } catch (StripeException ex) {
            throw new CreateTokenException(ex.getMessage());
        }
    }

    private Customer retrieveCustomer(String customerId) {
        try {
            return Customer.retrieve(customerId);
        } catch (StripeException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    private void updateCustomer(CustomerUpdateParams customerUpdateParams, Customer customer) {
        try {
            RequestOptions requestOptions = RequestOptions.builder()
                    .setApiKey(secretKey)
                    .build();
            customer.update(customerUpdateParams, requestOptions);
        } catch (StripeException ex) {
            throw new BalanceException(ex.getMessage());
        }
    }

    private Map<String, Object> createCardParams(CardRequest cardRequest) {
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", cardRequest.cardNumber());
        cardParams.put("exp_month", cardRequest.expMonth());
        cardParams.put("exp_year", cardRequest.expYear());
        cardParams.put("cvc", cardRequest.cvc());
        return cardParams;
    }

    private Map<String, Object> createChargeParams(ChargeRequest chargeRequest) {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.amount() * 100);
        chargeParams.put("currency", chargeRequest.currency());
        chargeParams.put("source", chargeRequest.token());
        return chargeParams;
    }
}