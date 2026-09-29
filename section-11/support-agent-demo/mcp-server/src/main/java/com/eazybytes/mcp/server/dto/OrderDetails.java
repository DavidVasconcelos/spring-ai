package com.eazybytes.mcp.server.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderDetails(
    String orderNumber,
    String customerName,
    String customerEmail,
    LocalDate orderDate,
    String status,
    String shippingAddress,
    BigDecimal totalAmount,
    String currency,
    List<OrderItemInfo> items,
    List<PaymentInfo> payments) {

}