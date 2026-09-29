package com.eazybytes.mcp.server.dto;

public record TicketLogResult(
    Long ticketId,
    String status,
    String summary) {

}