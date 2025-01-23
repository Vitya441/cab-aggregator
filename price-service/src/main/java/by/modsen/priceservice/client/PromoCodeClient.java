package by.modsen.priceservice.client;

import by.modsen.priceservice.dto.response.PromoCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "promocode-service")
public interface PromoCodeClient {

    @PutMapping("/api/v1/promocode/apply/{code}")
    PromoCodeResponse applyPromoCode(@PathVariable String code);
}