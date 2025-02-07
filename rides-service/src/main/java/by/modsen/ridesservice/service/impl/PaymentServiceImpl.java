package by.modsen.ridesservice.service.impl;

import by.modsen.ridesservice.client.PaymentClient;
import by.modsen.ridesservice.dto.request.CustomerChargeRequest;
import by.modsen.ridesservice.dto.response.ChargeResponse;
import by.modsen.ridesservice.exception.ServiceUnavailableException;
import by.modsen.ridesservice.service.PaymentService;
import by.modsen.ridesservice.util.ExceptionMessageConstants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentClient paymentClient;

    @Override
    @CircuitBreaker(name = "payment", fallbackMethod = "fallBackPaymentService")
    public ChargeResponse chargeFromCustomer(CustomerChargeRequest request) {
        return paymentClient.chargeFromCustomer(request);
    }

    private ChargeResponse fallBackPaymentService(Exception ex) {
        log.info("Payment service is not available. Fallback method was called from rides-service");
        throw new ServiceUnavailableException(ExceptionMessageConstants.PAYMENT_SERVICE_UNAVAILABLE);
    }
}