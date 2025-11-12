package com.sastachasma.order.payment;

import java.math.BigDecimal;

public interface PaymentService {
    boolean processPayment(String paymentMethod, BigDecimal amount, String transactionId);
}
