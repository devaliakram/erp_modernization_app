package com.example.erp.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ProductResponse(Long id,
                              String sku,
                              String name,
                              String category,
                              BigDecimal price,
                              Integer quantity,
                              Integer reorderLevel,
                              LocalDateTime createdDate,
                              LocalDateTime modifiedDate
) { }
