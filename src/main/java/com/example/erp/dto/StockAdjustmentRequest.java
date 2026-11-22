package com.example.erp.dto;

import jakarta.validation.constraints.NotNull;

// Positive number = stock in; negative number = stock out.
public record StockAdjustmentRequest(@NotNull Integer change) { }
