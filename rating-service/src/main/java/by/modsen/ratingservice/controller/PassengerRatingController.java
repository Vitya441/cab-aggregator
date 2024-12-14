package by.modsen.ratingservice.controller;

import by.modsen.ratingservice.dto.response.PassengerRatingResponse;
import by.modsen.ratingservice.service.PassengerRatingService;
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
@RequestMapping("api/v1/ratings/passengers")
@RequiredArgsConstructor
@Validated
public class PassengerRatingController {

    private final PassengerRatingService passengerRatingService;

    @GetMapping
    public ResponseEntity<List<PassengerRatingResponse>> getAllRecords() {
        return ResponseEntity.ok(passengerRatingService.getAllRatingRecords());
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<PassengerRatingResponse> getRatingByPassengerId(@PathVariable Long passengerId) {
        return ResponseEntity.ok(passengerRatingService.getRatingByPassengerId(passengerId));
    }

    @PostMapping("/{passengerId}")
    public ResponseEntity<PassengerRatingResponse> createRatingRecord(@PathVariable Long passengerId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(passengerRatingService.createRatingRecord(passengerId));
    }

    @PutMapping("/{passengerId}")
    public ResponseEntity<PassengerRatingResponse> updateRating(
            @PathVariable
            Long passengerId,
            @RequestParam
            @Min(value = 1, message = "Rating can't be less than 1")
            @Max(value = 5, message = "Rating can't be greater than 5")
            double rating
    ) {
        return ResponseEntity.ok(passengerRatingService.updateRatingRecord(passengerId, rating));
    }

    @DeleteMapping("/{passengerId}")
    public ResponseEntity<Void> deleteRatingByPassengerId(@PathVariable Long passengerId) {
        passengerRatingService.deleteRatingRecord(passengerId);
        return ResponseEntity.noContent().build();
    }
}