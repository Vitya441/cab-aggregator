package by.modsen.priceservice.controller;

import by.modsen.priceservice.dto.request.RideRequest;
import by.modsen.priceservice.service.PriceCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/price")
@RequiredArgsConstructor
public class PriceCalculationController {

    private final PriceCalculationService priceCalculationService;

    @PostMapping("/calculate")
    public ResponseEntity<BigDecimal> calculatePriceForRide(@RequestBody RideRequest rideRequest) {
        BigDecimal price = priceCalculationService.calculatePrice(rideRequest);
        return ResponseEntity.ok(price);
    }
}