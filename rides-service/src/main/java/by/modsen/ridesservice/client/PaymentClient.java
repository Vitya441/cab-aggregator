package by.modsen.ridesservice.client;

import by.modsen.ridesservice.config.FeignClientConfig;
import by.modsen.ridesservice.dto.request.CustomerChargeRequest;
import by.modsen.ridesservice.dto.response.ChargeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "${app.feign-clients.payment-service.url}",
        contextId = "paymentClient",
        configuration = FeignClientConfig.class
)
public interface PaymentClient {

    @PostMapping("/api/v1/payments/customers/charge")
    ChargeResponse chargeFromCustomer(@RequestBody CustomerChargeRequest request);
}