package by.modsen.priceservice.controller;

import by.modsen.commonmodule.enumeration.CarCategory;
import by.modsen.priceservice.dto.response.PaginatedResponse;
import by.modsen.priceservice.dto.response.TariffResponse;
import by.modsen.priceservice.dto.response.TariffResponseList;
import by.modsen.priceservice.entity.Tariff;
import by.modsen.priceservice.service.TariffService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/price/tariffs")
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;

    @GetMapping("/category/{carCategory}")
    public Tariff getByCarCategory(@PathVariable CarCategory carCategory) {
        return tariffService.getByCarCategory(carCategory);
    }

    @GetMapping
    public TariffResponseList getAll() {
        return tariffService.getAll();
    }

    @GetMapping("/page")
    public ResponseEntity<PaginatedResponse> getAllPaginated(
            @RequestParam(defaultValue = "0") @Min(0)
            int page,
            @RequestParam(defaultValue = "10") @Min(1)
            int size,
            @RequestParam(defaultValue = "id")
            String sortField

    ) {
        return ResponseEntity.ok(tariffService.getAllPaginated(page, size, sortField));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TariffResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tariffService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TariffResponse> update(
            @PathVariable Long id,
            @RequestParam BigDecimal costPerKm
            ) {
        return ResponseEntity.ok(tariffService.update(id, costPerKm));
    }
}