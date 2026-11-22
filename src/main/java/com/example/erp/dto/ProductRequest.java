package com.example.erp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

// Java record: data received from frontend on create/update.
public record ProductRequest(
        @NotBlank @Size(max = 32)
        String sku,
        @NotBlank @Size(max = 100)
        String name,

        @NotBlank @Size(max = 60) String category,
        @NotNull @DecimalMin("0.00") @Digits(integer = 10, fraction = 2) BigDecimal price,
        @NotNull @Min(0) Integer quantity,
        @NotNull @Min(0) Integer reorderLevel
) { }
