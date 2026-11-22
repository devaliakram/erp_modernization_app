package com.example.erp;

import com.example.erp.model.Product;
import com.example.erp.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void setUp() {

        // Clear existing test data
        repository.deleteAll();

        // Insert sample product into H2
        Product product = Product.builder()
                .sku("ERP-101")
                .productName("Laptop")
                .category("Electronics")
                .price(new BigDecimal("55000"))
                .quantity(10)
                .reorderLevel(5)
                .build();

        repository.save(product);
    }

    @Test
    void listsSeededProducts() throws Exception {

        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value("ERP-101"))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].quantity").value(10));
    }

    @Test
    void rejectsNegativeQuantity() throws Exception {

        String request = """
                {
                    "sku": "ERP-999",
                    "name": "Test",
                    "category": "Supplies",
                    "price": 100,
                    "quantity": -1,
                    "reorderLevel": 3
                }
                """;

        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
}