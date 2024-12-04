package by.modsen.passengerservice.controller;

import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.dto.request.PassengerUpdateDto;
import by.modsen.passengerservice.dto.response.PaginationDto;
import by.modsen.passengerservice.dto.response.PassengerDto;
import by.modsen.passengerservice.service.PassengerService;
import by.modsen.passengerservice.utils.MessageUtils;
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
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
@Validated
public class PassengerController {

    private final PassengerService service;

    @PostMapping
    public ResponseEntity<PassengerDto> create(@Valid @RequestBody PassengerCreateDto passengerCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(passengerCreateDto));
    }

    @GetMapping
    public ResponseEntity<PaginationDto<PassengerDto>> getAll(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = MessageUtils.VALIDATION_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = "15") @Min(value = 1, message = MessageUtils.VALIDATION_PAGE_SIZE) int size
    ) {
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getById(@PathVariable long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerDto> update(@PathVariable long id, @Valid @RequestBody PassengerUpdateDto passengerUpdateDto) {
        return ResponseEntity.ok(service.update(id, passengerUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}