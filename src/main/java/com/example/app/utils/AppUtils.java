package com.example.app.utils;

import com.example.app.model.Product;
import com.example.app.model.ProductDto;

public class AppUtils {

    public static ProductDto entityToDto(Product product){
     return ProductDto.builder()
             .productType(product.getProductType())
             .properties(product.getProperties())
             .price(product.getPrice())
             .storeAddress(product.getStore()+", "+product.getCity())
             .build();
    }
}
