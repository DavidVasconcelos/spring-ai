package com.eazybytes.mcp.server.dto;

import java.time.LocalDateTime;

public record TicketInfo(
    Long id,
    String ticketNumber,
    String subject,
    String intent,
    String sentiment,
    String status,
    String resolution,
    LocalDateTime createdAt) {

}