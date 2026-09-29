package com.eazybytes.mcp.server.dto;

import java.math.BigDecimal;

public record OrderItemInfo(
    String sku,
    String productName,
    Integer quantity,
    BigDecimal unitPrice) {

}