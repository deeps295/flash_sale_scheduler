package com.flashsale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.flashsale.model.Product;
import com.flashsale.service.ProductService;

import java.math.BigDecimal;

@SpringBootApplication
public class FlashSaleApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlashSaleApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(ProductService productService) {
        return args -> {
            if (productService.count() == 0) {
                productService.create(Product.builder()
                        .name("Wireless Headphones")
                        .description("Noise-cancelling over-ear headphones")
                        .price(new BigDecimal("129.99"))
                        .stock(50)
                        .active(true)
                        .build());
                productService.create(Product.builder()
                        .name("Smart Watch")
                        .description("Waterproof fitness tracking smart watch")
                        .price(new BigDecimal("89.99"))
                        .stock(100)
                        .active(true)
                        .build());
                productService.create(Product.builder()
                        .name("Gaming Mouse")
                        .description("RGB ergonomic gaming mouse")
                        .price(new BigDecimal("39.99"))
                        .stock(200)
                        .active(true)
                        .build());
            }
        };
    }
}
