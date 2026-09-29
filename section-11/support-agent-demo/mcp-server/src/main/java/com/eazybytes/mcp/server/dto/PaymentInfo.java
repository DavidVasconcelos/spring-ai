package com.eazybytes.mcp.server.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentInfo(
    Long id,
    BigDecimal amount,
    String currency,
    String method,
    String transactionRef,
    String status,
    LocalDateTime chargedAt) {

}