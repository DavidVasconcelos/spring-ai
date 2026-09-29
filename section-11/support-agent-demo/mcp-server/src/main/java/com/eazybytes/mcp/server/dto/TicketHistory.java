package com.eazybytes.mcp.server.dto;

import java.util.List;

public record TicketHistory(
    String customerEmail,
    int totalTickets,
    List<TicketInfo> tickets,
    String summary) {

}