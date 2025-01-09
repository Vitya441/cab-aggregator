package org.example.paymentservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.paymentservice.dto.request.CardRequest;
import org.example.paymentservice.dto.request.ChargeRequest;
import org.example.paymentservice.dto.request.CustomerChargeRequest;
import org.example.paymentservice.dto.request.CustomerRequest;
import org.example.paymentservice.dto.response.CardTokenResponse;
import org.example.paymentservice.dto.response.ChargeResponse;
import org.example.paymentservice.dto.response.CustomerResponse;
import org.example.paymentservice.service.impl.PaymentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentServiceImpl paymentServiceImpl;

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest request) {
        CustomerResponse response = paymentServiceImpl.createCustomer(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok(paymentServiceImpl.getCustomer(customerId));
    }

    @PostMapping("/customers/charge")
    public ResponseEntity<ChargeResponse> chargeFromCustomer(@RequestBody @Valid CustomerChargeRequest request) {
        return ResponseEntity.ok(paymentServiceImpl.chargeFromCustomer(request));
    }

    @PostMapping("/generate-token")
    public ResponseEntity<CardTokenResponse> generateCardToken(@RequestBody @Valid CardRequest request) {
        CardTokenResponse response = paymentServiceImpl.generateToken(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/charge")
    public ResponseEntity<String> chargeCard(@RequestBody @Valid ChargeRequest request) {
        String response = paymentServiceImpl.chargeCard(request);
        return ResponseEntity.ok(response);
    }
}