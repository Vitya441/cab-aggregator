package org.example.paymentservice.service.impl;

import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.paymentservice.dto.request.CardRequest;
import org.example.paymentservice.dto.request.ChargeRequest;
import org.example.paymentservice.dto.request.CustomerChargeRequest;
import org.example.paymentservice.dto.request.CustomerRequest;
import org.example.paymentservice.dto.response.CardTokenResponse;
import org.example.paymentservice.dto.response.ChargeResponse;
import org.example.paymentservice.dto.response.CustomerResponse;
import org.example.paymentservice.entity.Account;
import org.example.paymentservice.exception.BalanceException;
import org.example.paymentservice.exception.NotFoundException;
import org.example.paymentservice.repository.AccountRepository;
import org.example.paymentservice.service.PaymentService;
import org.example.paymentservice.service.StripeService;
import org.example.paymentservice.util.ExceptionConstants;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final StripeService stripeService;
    private final AccountRepository accountRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = stripeService.createStripeCustomer(customerRequest);
        stripeService.createPayment(customer.getId());
        saveAccountToDatabase(customer);

        return CustomerResponse.builder()
                .customerId(customer.getId())
                .name(customer.getName())
                .balance(customer.getBalance())
                .build();
    }

    @Override
    public CustomerResponse getCustomer(String customerId) {
        Account account = getAccountByCustomerId(customerId);

        return CustomerResponse.builder()
                .customerId(account.getCustomerId())
                .name(account.getName())
                .balance(account.getBalance())
                .build();
    }

    @Override
    public ChargeResponse chargeFromCustomer(CustomerChargeRequest chargeRequest) {
        Account account = getAccountByCustomerId(chargeRequest.customerId());
        String customerId = account.getCustomerId();

        checkMoneyIsEnough(account, chargeRequest.amount());
        PaymentIntent paymentIntent = stripeService.confirmIntent(chargeRequest, customerId);
        stripeService.changeBalance(customerId, chargeRequest.amount());

        account.setBalance(account.getBalance() - chargeRequest.amount());
        accountRepository.save(account);

        return ChargeResponse.builder()
                .id(paymentIntent.getId())
                .amount(paymentIntent.getAmount() / 100)
                .currency(paymentIntent.getCurrency())
                .build();
    }

    @Override
    public CardTokenResponse generateToken(CardRequest cardRequest) {
        Token token = stripeService.createToken(cardRequest);
        return new CardTokenResponse(token.getId());
    }

    @Override
    public String chargeCard(ChargeRequest chargeRequest) {
        Charge charge = stripeService.createCharge(chargeRequest);
        return charge.getId();
    }

    private void saveAccountToDatabase(Customer customer) {
        Account account = Account.builder()
                .customerId(customer.getId())
                .balance(customer.getBalance())
                .name(customer.getName())
                .build();
        accountRepository.save(account);
    }

    private Account getAccountByCustomerId(String customerId) {
        return accountRepository
                .findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionConstants.CUSTOMER_NOT_FOUND, customerId)));
    }

    private void checkMoneyIsEnough(Account account, Long amount) {
        if (account.getBalance() < amount) {
            throw new BalanceException(ExceptionConstants.NOT_ENOUGH_MONEY);
        }
    }
}