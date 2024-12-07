package by.modsen.ridesservice.controller;

import by.modsen.commonmodule.dto.RideRequest;
import by.modsen.commonmodule.dto.RideResponse;
import by.modsen.ridesservice.service.RideService;
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
    public ResponseEntity<RideResponse> getCurrentRide(@RequestParam Long passengerId) {
        return ResponseEntity.ok(rideService.getCurrentRide(passengerId));
    }

    @PutMapping("/{rideId}/confirm")
    public void confirmRide(@PathVariable Long rideId, @RequestParam Long driverId) {
        rideService.confirmRide(rideId, driverId);
    }
    @PutMapping("{rideId}/reject")
    public void rejectRide(@PathVariable Long rideId, @RequestParam Long driverId) {
        rideService.rejectRide(rideId, driverId);
    }
}