package com.example.erp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {
    @Autowired private MockMvc mvc;

    @Test
    void listsSeededProducts() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value("ERP-101"));
    }

    @Test
    void rejectsNegativeQuantity() throws Exception {
        String request = """
                {"sku":"ERP-999","name":"Test","category":"Supplies",
                 "price":100,"quantity":-1,"reorderLevel":3}
                """;
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isBadRequest());
    }
}
