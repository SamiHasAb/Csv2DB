package com.example.app.service;

import com.example.app.model.ProductWrapper;
import com.example.app.model.ProductProperties;
import com.example.app.model.ProductType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {

    ProductWrapper searchProduct(ProductType type, BigDecimal min_price, BigDecimal max_price, String city, ProductProperties property, String color, Integer gb_limit_min, Integer gb_limit_max);

//    public String extractDataFromFile(MultipartFile file) throws IOException;

    Integer saveDataToDB() throws IOException;

    Integer extractDataFromFile(MultipartFile file) throws IOException;
}
