package com.sastachasma.order.mapper;

import com.sastachasma.order.dto.OrderStatusHistoryDTO;
import com.sastachasma.order.entity.OrderStatusHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Mapper(componentModel = "spring")
public abstract class OrderStatusHistoryMapper {

    public static OrderStatusHistoryMapper INSTANCE = Mappers.getMapper(OrderStatusHistoryMapper.class);

    public OrderStatusHistoryDTO toDto(OrderStatusHistory statusHistory) {
        if (statusHistory == null) {
            return null;
        }
        
        return OrderStatusHistoryDTO.builder()
                .id(statusHistory.getId())
                .status(statusHistory.getStatus())
                .description(statusHistory.getDescription())
//                .statusDate(statusHistory.getStatusDate())
                .build();
    }

    public OrderStatusHistory toEntity(OrderStatusHistoryDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return OrderStatusHistory.builder()
                .id(dto.getId())
                .status(dto.getStatus())
                .description(dto.getDescription())
//                .statusDate(dto.getStatusDate() != null ? dto.getStatusDate() : LocalDateTime.now())
                // order will be set by the parent Order
                .build();
    }
    
    public OrderStatusHistory createStatusHistory(String status, String description) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        
        return OrderStatusHistory.builder()
                .status(status)
                .description(description)
                .statusDate(LocalDateTime.now())
                .build();
    }
}
