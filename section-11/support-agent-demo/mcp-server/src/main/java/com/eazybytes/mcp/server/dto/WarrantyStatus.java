package com.eazybytes.mcp.server.dto;

import java.time.LocalDate;

public record WarrantyStatus(
    String orderNumber,
    String sku,
    String productName,
    LocalDate orderDate,
    int warrantyMonths,
    LocalDate warrantyEndDate,
    boolean inWarranty,
    String summary) {

}