package com.example.app;

import com.example.app.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SearchProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchProductsApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(ProductService productService){
//        return args -> {
//            productService.saveDataToDB();
//        };
//    }
}
