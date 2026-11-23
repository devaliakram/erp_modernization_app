package com.example.erp.dto;

import jakarta.validation.constraints.NotNull;

public record StockAdjustmentRequest(@NotNull Integer change) {
}
