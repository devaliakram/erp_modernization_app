package com.example.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ErpInventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ErpInventoryApplication.class, args);
    }
}
