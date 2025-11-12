package com.sastachasma.payment.controller;

import com.sastachasma.payment.dto.PaymentDTO;
import com.sastachasma.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment", description = "APIs for managing payments")
@RestController
@RequestMapping("/payments") // Aligned with API Gateway path rewriting
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Process a new payment",
              description = "Initiates and processes a new payment transaction")
    @ApiResponse(responseCode = "201",
                 description = "Payment processed successfully",
                 content = @Content(schema = @Schema(implementation = PaymentDTO.class)))
    @PostMapping
    public ResponseEntity<PaymentDTO> processPayment(@RequestBody PaymentDTO paymentDTO) {
        PaymentDTO processedPayment = paymentService.processPayment(paymentDTO);
        return new ResponseEntity<>(processedPayment, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve payment details by ID",
              description = "Fetches the details of a specific payment by its ID")
    @ApiResponse(responseCode = "200",
                 description = "Payment details retrieved successfully",
                 content = @Content(schema = @Schema(implementation = PaymentDTO.class)))
    @ApiResponse(responseCode = "404",
                 description = "Payment not found")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        PaymentDTO paymentDTO = paymentService.getPaymentById(id);
        return ResponseEntity.ok(paymentDTO);
    }
}
