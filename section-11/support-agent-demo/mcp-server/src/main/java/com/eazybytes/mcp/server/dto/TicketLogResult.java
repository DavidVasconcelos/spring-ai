package com.eazybytes.mcp.server.dto;

public record TicketLogResult(
    Long ticketId,
    String ticketNumber,
    String status,
    String summary) {

}