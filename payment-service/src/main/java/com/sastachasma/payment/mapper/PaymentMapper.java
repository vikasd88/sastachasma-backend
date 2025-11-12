package com.sastachasma.payment.mapper;

import com.sastachasma.payment.dto.PaymentDTO;
import com.sastachasma.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toDto(Payment payment);
    Payment toEntity(PaymentDTO paymentDTO);
    void updatePaymentFromDto(PaymentDTO paymentDTO, @MappingTarget Payment payment);
}
