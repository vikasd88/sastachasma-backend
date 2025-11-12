package com.sastachasma.order.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Override
    public boolean processPayment(String paymentMethod, BigDecimal amount, String transactionId) {
        log.info("Processing payment for amount {} with method {} and transaction ID {}", amount, paymentMethod, transactionId);
        // Simulate payment processing logic
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            log.info("Payment successful for transaction ID {}", transactionId);
            return true;
        } else {
            log.warn("Payment failed for transaction ID {}: Invalid amount", transactionId);
            return false;
        }
    }
}
