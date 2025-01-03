package by.modsen.promocodeservice.controller;

import by.modsen.promocodeservice.dto.PaginatedResponse;
import by.modsen.promocodeservice.dto.PromoCodeRequest;
import by.modsen.promocodeservice.dto.PromoCodeResponse;
import by.modsen.promocodeservice.dto.PromoCodeResponseList;
import by.modsen.promocodeservice.service.PromoCodeService;
import by.modsen.promocodeservice.util.ExceptionMessageConstants;
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
@RequestMapping("api/v1/promocode")
@RequiredArgsConstructor
@Validated
public class PromoCodeController {

    private final PromoCodeService promoCodeService;

    @GetMapping("{id}")
    public ResponseEntity<PromoCodeResponse> getPromoCodeById(@PathVariable String id) {
        return ResponseEntity.ok(promoCodeService.getById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<PromoCodeResponse> getPromoCodeByCode(@PathVariable String code) {
        return ResponseEntity.ok(promoCodeService.getByCode(code));
    }

    @GetMapping
    public ResponseEntity<PromoCodeResponseList> getAllPromoCodes() {
        return ResponseEntity.ok(promoCodeService.getAll());
    }

    @GetMapping("/page")
    public ResponseEntity<PaginatedResponse> getPromoCodePage(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = ExceptionMessageConstants.VALIDATION_PAGE_NUMBER_MIN)
            int page,
            @RequestParam(defaultValue = "15") @Min(value = 1, message = ExceptionMessageConstants.VALIDATION_PAGE_SIZE_MIN)
            int size,
            @RequestParam(defaultValue = "id")
            String sortField
    ) {
        return ResponseEntity.ok(promoCodeService.getAllPaginated(page, size, sortField));
    }

    @PostMapping
    public ResponseEntity<PromoCodeResponse> createPromoCode(
            @RequestBody @Valid
            PromoCodeRequest promoCodeRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promoCodeService.create(promoCodeRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<PromoCodeResponse> updatePromoCode(
            @PathVariable
            String id,
            @RequestBody @Valid
            PromoCodeRequest promoCodeRequest
    ) {
        return ResponseEntity.ok(promoCodeService.update(id, promoCodeRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePromoCode(@PathVariable String id) {
        promoCodeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}