package by.modsen.ridesservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rating-service")
public interface RatingClient {

    @PutMapping("api/v1/ratings/drivers/{driverId}")
    void updateDriverRating(@PathVariable Long driverId, @RequestParam double rating);

    @PutMapping("api/v1/ratings/passengers/{passengerId}")
    void updatePassengerRating(@PathVariable Long passengerId, @RequestParam double rating);
}