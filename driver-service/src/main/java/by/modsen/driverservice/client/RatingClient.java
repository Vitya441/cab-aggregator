package by.modsen.driverservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(contextId = "ratingClient",name = "${app.feign-clients.rating-service.url}")
public interface RatingClient {

    @PostMapping("api/v1/ratings/drivers/{driverId}")
    void createDriverRatingRecord(@PathVariable Long driverId);
}