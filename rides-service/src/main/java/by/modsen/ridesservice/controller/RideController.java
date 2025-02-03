package by.modsen.ridesservice.controller;

import by.modsen.ridesservice.dto.request.RideRequest;
import by.modsen.ridesservice.dto.response.PaginationDto;
import by.modsen.ridesservice.dto.response.RideResponse;
import by.modsen.ridesservice.dto.response.RideWithDriverResponse;
import by.modsen.ridesservice.service.RideService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping
    public ResponseEntity<Void> requestRide(@RequestBody RideRequest rideRequest) {
        rideService.requestRide(rideRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/current")
    public ResponseEntity<RideWithDriverResponse> getCurrentRide(@RequestParam Long passengerId) {
        return ResponseEntity.ok(rideService.getActiveRide(passengerId));
    }

    @GetMapping("/passenger/history/{passengerId}")
    public ResponseEntity<PaginationDto<RideResponse>> getHistoryByPassengerId(
            @PathVariable
            Long passengerId,
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "Min value is 0")
            int page,
            @RequestParam(defaultValue = "15") @Min(value = 1, message = "Min value is 1")
            int size,
            @RequestParam(name = "sort", defaultValue = "id")
            String sortField
    ) {
        return ResponseEntity.ok(rideService.getHistoryByPassengerId(passengerId, page, size, sortField));
    }

    @GetMapping("/driver/history/{driverId}")
    public ResponseEntity<PaginationDto<RideResponse>> getHistoryByDriverId(
            @PathVariable
            Long driverId,
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "Min value is 0")
            int page,
            @RequestParam(defaultValue = "15") @Min(value = 1, message = "Min value is 1")
            int size,
            @RequestParam(name = "sort", defaultValue = "id")
            String sortField
    ) {
        return ResponseEntity.ok(rideService.getHistoryByDriverId(driverId, page, size, sortField));
    }

    @PutMapping("/{rideId}/confirm")
    public ResponseEntity<Void> confirmRide(@PathVariable Long rideId, @RequestParam Long driverId) {
        rideService.confirmRide(rideId, driverId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{rideId}/reject")
    public ResponseEntity<Void> rejectRide(@PathVariable Long rideId, @RequestParam Long driverId) {
        rideService.rejectRide(rideId, driverId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{rideId}/start")
    public ResponseEntity<Void> startRide(@PathVariable Long rideId, @RequestParam Long driverId) {
        rideService.startRide(rideId, driverId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{rideId}/end")
    public ResponseEntity<Void> endRide(@PathVariable Long rideId, @RequestParam Long driverId) {
        rideService.endRide(rideId, driverId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{rideId}/rate-driver")
    public ResponseEntity<Void> rateDriver(@PathVariable Long rideId, @RequestParam double rating) {
        rideService.rateDriver(rideId, rating);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{rideId}/rate-passenger")
    public ResponseEntity<Void> ratePassenger(@PathVariable Long rideId, @RequestParam double rating) {
        rideService.ratePassenger(rideId, rating);
        return ResponseEntity.ok().build();
    }
}