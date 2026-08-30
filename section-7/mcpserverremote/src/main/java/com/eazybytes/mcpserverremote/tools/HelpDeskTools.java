package com.eazybytes.mcpserverremote.tools;

import com.eazybytes.mcpserverremote.entity.HelpDeskTicket;
import com.eazybytes.mcpserverremote.model.TicketRequest;
import com.eazybytes.mcpserverremote.service.HelpDeskTicketService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.ai.mcp.annotation.context.McpSyncRequestContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpDeskTools {

  private static final Logger logger = LoggerFactory.getLogger(HelpDeskTools.class);

  private final HelpDeskTicketService service;

  @McpTool(name = "createTicket", description = "Create the Support Ticket")
  String createTicket(@McpToolParam(description = "Details to create a Support ticket")
  TicketRequest ticketRequest) {
    logger.info("Creating support ticket for user: {} with details: {}", ticketRequest.username(),
        ticketRequest.issue());
    HelpDeskTicket savedTicket = service.createTicket(ticketRequest);
    logger.info("Ticket created successfully. Ticket ID: {}, Username: {}", savedTicket.getId(),
        savedTicket.getUsername());
    return "Ticket #" + savedTicket.getId() + " created successfully for the user "
        + savedTicket.getUsername();
  }

  @McpTool(name = "getTicketStatus",
      description = "Fetch the status of the open tickets based on a given username")
  List<HelpDeskTicket> getTicketStatus(
      @McpToolParam(description = "Username to fetch the status of the help desk tickets")
      String username, McpSyncRequestContext context) {
    String entranceLogMessage = String.format("Fetching tickets for user: %s", username);
    logger.info(entranceLogMessage);
    context.info(entranceLogMessage);
    List<HelpDeskTicket> tickets = service.getTicketsByUsername(username);
    String exitLogMessage = String.format("Found %d tickets for user: %s", tickets.size(),
        username);
    logger.info(exitLogMessage);
    context.info(exitLogMessage);
    return tickets;
  }
}
