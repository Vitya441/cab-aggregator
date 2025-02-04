package by.modsen.ridesservice.service;

import by.modsen.ridesservice.dto.request.CustomerChargeRequest;
import by.modsen.ridesservice.dto.response.ChargeResponse;

public interface PaymentService {
    ChargeResponse chargeFromCustomer(CustomerChargeRequest request);
}