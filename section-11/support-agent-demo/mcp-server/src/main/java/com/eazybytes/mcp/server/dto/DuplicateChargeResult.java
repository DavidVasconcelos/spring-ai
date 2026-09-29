package com.eazybytes.mcp.server.dto;

import java.math.BigDecimal;
import java.util.List;

public record DuplicateChargeResult(
    String orderNumber,
    boolean duplicateDetected,
    int chargeCount,
    BigDecimal totalCharged,
    BigDecimal expectedAmount,
    BigDecimal overchargedAmount,
    List<PaymentInfo> charges,
    String summary) {

}