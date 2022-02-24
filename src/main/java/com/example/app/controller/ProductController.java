package com.example.app.controller;

import com.example.app.exceptions.WrongSearchCombinationException;
import com.example.app.model.ProductWrapper;
import com.example.app.model.ProductProperties;
import com.example.app.model.ProductType;
import com.example.app.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/product")
    public ResponseEntity<?> getProducts(
            @RequestParam(value = "type", required = false ) ProductType type,
            @RequestParam(value = "min_price", required = false) BigDecimal min_price,
            @RequestParam(value = "max_price", required = false ) BigDecimal max_price,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "property", required = false) ProductProperties property,
            @RequestParam(value = "property:color", required = false) String color,
            @RequestParam(value = "property:gb_limit_min", required = false) Integer gb_limit_min,
            @RequestParam(value = "property:gb_limit_max", required = false) Integer gb_limit_max

    ) {


        if ((gb_limit_min != null || gb_limit_max != null)&& type != ProductType.subscription){
            log.error("Invalid search combination , Hint: product must be of a type \"phone\" to search by gb_limit," +
                    "product type of \"{}\" was entered");
            throw new WrongSearchCombinationException("Invalid search combination");


        }

        ProductWrapper productWrapper = productService
                .searchProduct(type, min_price, max_price, city, property,
                        color, gb_limit_min,gb_limit_max);
        return  new ResponseEntity<ProductWrapper>(productWrapper, HttpStatus.OK);

    }

    @PostMapping("/upload")
    public ResponseEntity<?>  uploadData(@RequestParam("file") MultipartFile file) throws Exception {
        Integer totalNumberOfRecord = productService.extractDataFromFile(file);
        String res = "Nunmber of product uploaded "+ totalNumberOfRecord.toString();
        return new ResponseEntity<String>(res, HttpStatus.OK);
    }

    @GetMapping("/saveToDB")
    public ResponseEntity<?> saveDataToDB() throws IOException {
        Integer numberOfUploadedRecord =  productService.saveDataToDB();
        log.info("Nunmber of product uploaded {}", numberOfUploadedRecord);
        String response = "Nunmber of product uploaded "+ numberOfUploadedRecord.toString();
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

}
