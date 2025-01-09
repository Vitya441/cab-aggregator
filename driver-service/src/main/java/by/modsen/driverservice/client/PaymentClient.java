package by.modsen.driverservice.client;

import by.modsen.driverservice.dto.request.CustomerRequest;
import by.modsen.driverservice.dto.response.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/api/v1/payments/customers")
    CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest);
}