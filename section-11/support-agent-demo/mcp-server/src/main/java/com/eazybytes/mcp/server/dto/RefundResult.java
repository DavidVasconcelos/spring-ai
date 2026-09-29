package com.eazybytes.mcp.server.dto;

import java.math.BigDecimal;

public record RefundResult(
    Long refundId,
    String refundNumber,
    String orderNumber,
    BigDecimal amount,
    String currency,
    String refundType,
    String status,
    String summary) {

}