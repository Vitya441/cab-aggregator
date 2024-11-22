package by.modsen.driverservice.controller;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.DriverWithCarDto;
import by.modsen.driverservice.dto.response.PaginationDto;
import by.modsen.driverservice.service.DriverService;
import by.modsen.driverservice.util.ExceptionMessageKeyConstants;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
@Validated
public class DriverController {

    private final DriverService driverService;

    @GetMapping
    public ResponseEntity<PaginationDto<DriverDto>> getAll(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = ExceptionMessageKeyConstants.VALIDATION_PAGE_NUMBER)
            int page,
            @RequestParam(defaultValue = "15") @Min(value = 1, message = ExceptionMessageKeyConstants.VALIDATION_PAGE_SIZE)
            int size,
            @RequestParam(name = "sort", defaultValue = "id")
            String sortField
    ) {
        return ResponseEntity.ok(driverService.getAll(page, size, sortField));
    }

    @GetMapping("/with-car")
    public ResponseEntity<PaginationDto<DriverWithCarDto>> getAllWithCar(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = ExceptionMessageKeyConstants.VALIDATION_PAGE_NUMBER)
            int page,
            @RequestParam(defaultValue = "15") @Min(value = 1, message = ExceptionMessageKeyConstants.VALIDATION_PAGE_SIZE)
            int size,
            @RequestParam(name = "sort", defaultValue = "id")
            String sortField
    ) {
        return ResponseEntity.ok(driverService.getAllWithCar(page, size, sortField));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getById(id));
    }

    @GetMapping("/with-car/{id}")
    public ResponseEntity<DriverWithCarDto> getByIdWithCar(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getByIdWithCar(id));
    }

    @PostMapping
    public ResponseEntity<DriverDto> create(@Valid @RequestBody DriverCreateDto driverCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.create(driverCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDto> update(@PathVariable Long id, @Valid @RequestBody DriverCreateDto driverCreateDto) {
        return ResponseEntity.ok(driverService.update(id, driverCreateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        driverService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/assign-car")
    public ResponseEntity<?> assignCar(@PathVariable long id, @RequestParam long carId) {
        driverService.assignCarToDriver(id, carId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/unassign-car")
    public ResponseEntity<?> unAssignCar(@PathVariable long id) {
        driverService.unAssignCarFromDriver(id);
        return ResponseEntity.noContent().build();
    }
}