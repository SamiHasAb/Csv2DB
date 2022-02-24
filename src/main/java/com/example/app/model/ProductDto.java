package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class ProductDto {
    private String productType;
    private String properties;
    private BigDecimal price;
    @JsonProperty("store_address")
    private String storeAddress;
}
