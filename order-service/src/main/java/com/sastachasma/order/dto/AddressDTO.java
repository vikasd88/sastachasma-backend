package com.sastachasma.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phone;
    
    @Override
    public String toString() {
        return String.format("%s, %s, %s %s, %s", 
            street, city, state, postalCode, country);
    }
}
