package by.modsen.ratingservice.controller;

import by.modsen.ratingservice.dto.response.DriverRatingResponse;
import by.modsen.ratingservice.service.DriverRatingService;
import by.modsen.ratingservice.util.ExceptionMessageConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/ratings/drivers")
@RequiredArgsConstructor
@Validated
public class DriverRatingController {

    private final DriverRatingService driverRatingService;

    @GetMapping
    public ResponseEntity<List<DriverRatingResponse>> getAllRecords() {
        return ResponseEntity.ok(driverRatingService.getAllRatingRecords());
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverRatingResponse> getRatingByDriverId(@PathVariable Long driverId) {
        return ResponseEntity.ok(driverRatingService.getRatingByDriverId(driverId));
    }

    @PostMapping("/{driverId}")
    public ResponseEntity<DriverRatingResponse> createRatingRecord(@PathVariable Long driverId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driverRatingService.createRatingRecord(driverId));
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<DriverRatingResponse> updateRating(
            @PathVariable
            Long driverId,
            @RequestParam
            @Min(value = 1, message = ExceptionMessageConstants.VALIDATION_RATING_MIN)
            @Max(value = 5, message = ExceptionMessageConstants.VALIDATION_RATING_MAX)
            double rating
    ) {
        return ResponseEntity.ok(driverRatingService.updateRatingRecord(driverId, rating));
    }

    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> deleteRatingByDriverId(@PathVariable Long driverId) {
        driverRatingService.deleteRatingRecord(driverId);
        return ResponseEntity.noContent().build();
    }
}