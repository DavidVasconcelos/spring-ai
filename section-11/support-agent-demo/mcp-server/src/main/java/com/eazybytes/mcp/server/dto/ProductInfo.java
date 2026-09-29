package com.eazybytes.mcp.server.dto;

import java.math.BigDecimal;

public record ProductInfo(
    Long id,
    String sku,
    String name,
    String description,
    String category,
    BigDecimal price,
    String currency,
    String specifications,
    Integer warrantyMonths,
    Integer stockQuantity) {

}