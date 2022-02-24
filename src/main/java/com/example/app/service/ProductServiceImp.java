package com.example.app.service;

import com.example.app.model.*;
import com.example.app.repository.ProductRepository;
import com.example.app.utils.AppUtils;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImp implements ProductService {

    public static final String TYPE_SUBSCRIPTION ="subscription";

    private final ProductRepository productRepository;

    List<Product> productList = new ArrayList<>();
    List<Product> filteredList = new ArrayList<>();

    @Override
    public ProductWrapper searchProduct(ProductType type, BigDecimal min_price, BigDecimal max_price,
                                        String city, ProductProperties property, String color,
                                        Integer gb_limit_min, Integer gb_limit_max) {


    log.info("Search criteria: type= {},min_price= {}," +
            "max_price= {},city= {}," +
            "property= {},color= {}," +
            "gb_limit_min= {},gb_limit_max= {}",
            type, min_price,max_price,city, property,
            color, gb_limit_min, gb_limit_max);

    log.info("Search has started......");

            filteredList = productRepository.findAll().stream()
                    .filter(product -> {
                                if (type != null)
                                    return product.getProductType().equalsIgnoreCase(type.toString());
                                return true;
                            }
                    )
                    .filter(product -> {
                                if (max_price != null)
                                    return product.getPrice().compareTo(max_price) <= 0;
                                return true;
                            }
                    )
                    .filter(product -> {
                                if (min_price != null)
                                    return product.getPrice().compareTo(min_price) >= 0;
                                return true;
                            }
                    )
                    .filter(product -> {
                        if (city != null)
                            return product.getCity().equalsIgnoreCase(city);
                        return true;
                    })
                    .filter(product -> {
                        if(property != null)
                            return product.getProperties().contains(property.toString());
                        return true;
                    })
                    .filter(product -> {
                        if(color != null)
                            return product.getProperties().contains(color);
                        return true;
                    } )
                    .filter(product -> {
                        if(gb_limit_min != null  )
                        {
                            return  Integer.parseInt( product.getProperties().substring(9)) >= gb_limit_min;
                        }
                        return true;
                    })
                    .filter(product -> {
                        if(gb_limit_max != null && type.toString().equalsIgnoreCase(TYPE_SUBSCRIPTION)  )
                        {
                            return
                                    Integer.parseInt( product.getProperties().substring(9)) <= gb_limit_max ;
                        }
                        return true;
                    })
                    .collect(Collectors.toList());

        List<ProductDto> filteredListDto =  filteredList.stream()
                .map(AppUtils::entityToDto)
                .collect(Collectors.toList());
        log.info("Number of product found {}", filteredListDto.size());
        return  ProductWrapper.builder()
                .data(filteredListDto)
                .build();
    }

     public Integer saveDataToDB() throws IOException {
        String line = "";
        List<Product> newProductList = new ArrayList<>();
        log.info("uploading csv file");
        BufferedReader br = new BufferedReader((new FileReader("src/main/resources/data.csv")));
         log.info("uploading csv file started.......");
         br.readLine(); //to skip the 1st line
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            Product fitchedProductFromCSVFile = new Product();
            fitchedProductFromCSVFile.setProductType(data[0]);
            fitchedProductFromCSVFile.setProperties(data[1]);
            fitchedProductFromCSVFile.setPrice(new BigDecimal(data[2]));
            fitchedProductFromCSVFile.setStore(data[3].replace("\"", ""));
            fitchedProductFromCSVFile.setCity(data[4].replace("\"", "").trim());
            productList.add(fitchedProductFromCSVFile);
        }
         productRepository.saveAllAndFlush(productList);
         log.info("Total number of product saved : {}", productList.size());
        return productList.size();
    }

    @Override
    public Integer extractDataFromFile(MultipartFile file) throws IOException {
        log.info("Fetching data from the uploaded csv file started");
        List<Product> productList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParserSettings setting = new CsvParserSettings();
        setting.setHeaderExtractionEnabled(true); //remove header
        CsvParser parser = new CsvParser(setting);
        List<Record> allRecords = parser.parseAllRecords(inputStream);
        allRecords.forEach(
                record -> {
                    Product product = new Product();
                    product.setProductType(record.getString("Product type"));
                    product.setProperties(record.getString("Product properties"));
                    product.setPrice(new BigDecimal(record.getString("Price")));
                    product.setStore(record.getString("Store address").replace("\"",""));
                    product.setCity(record.getString("Store address").replace("\"","").trim());
                    productList.add(product);
                });
        productRepository.saveAllAndFlush(productList);
        log.info("Total number of product saved : {}", productList.size());
        return productList.size();
    }

}
