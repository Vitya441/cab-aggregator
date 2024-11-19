package by.modsen.driverservice.controller;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.service.CarService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<Page<CarDto>> getAll(
            @RequestParam(defaultValue = "0") @Min(value = 0)
            int page,
            @RequestParam(defaultValue = "15") @Min(value = 1)
            int size,
            @RequestParam(name = "sort", defaultValue = "id")
            String sortField
    ) {
        return ResponseEntity.ok(carService.getAll(page, size, Sort.by(Sort.Direction.ASC, sortField)));
    }

    @GetMapping("/available")
    public ResponseEntity<Page<CarDto>> getAvailable(
            @RequestParam(defaultValue = "0") @Min(value = 0)
            int page,
            @RequestParam(defaultValue = "15") @Min(value = 1)
            int size,
            @RequestParam(name = "sort", defaultValue = "id")
            String sortField
    ) {
        return ResponseEntity.ok(carService.getAvailable(page, size, Sort.by(Sort.Direction.ASC, sortField)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CarDto> create(@RequestBody CarCreateDto carCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.create(carCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> update(@PathVariable Long id, @RequestBody CarCreateDto carCreateDto) {
        return ResponseEntity.ok(carService.update(id, carCreateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}