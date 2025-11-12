package com.sastachasma.payment.service;

import com.sastachasma.payment.dto.PaymentDTO;

public interface PaymentService {
    PaymentDTO processPayment(PaymentDTO paymentDTO);
    PaymentDTO getPaymentById(Long id);
}
