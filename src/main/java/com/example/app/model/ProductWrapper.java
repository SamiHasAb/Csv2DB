package com.example.app.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ProductWrapper {

    private List<ProductDto> data;

}
