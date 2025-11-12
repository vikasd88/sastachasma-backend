package com.sastachasma.payment.service.impl;

import com.sastachasma.payment.dto.PaymentDTO;
import com.sastachasma.payment.entity.Payment;
import com.sastachasma.payment.exception.ResourceNotFoundException;
import com.sastachasma.payment.mapper.PaymentMapper;
import com.sastachasma.payment.repository.PaymentRepository;
import com.sastachasma.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDTO processPayment(PaymentDTO paymentDTO) {
        Payment payment = paymentMapper.toEntity(paymentDTO);
        // Simulate a successful payment transaction ID
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setStatus("COMPLETED"); // Assume success for dummy implementation
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return paymentMapper.toDto(payment);
    }
}
