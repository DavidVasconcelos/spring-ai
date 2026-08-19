package com.eazybytes.springai.tools;

import com.eazybytes.springai.entity.HelpDeskTicket;
import com.eazybytes.springai.model.TicketRequest;
import com.eazybytes.springai.service.HelpDeskTicketService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpDeskTools {

  private static final Logger logger = LoggerFactory.getLogger(HelpDeskTools.class);

  private final HelpDeskTicketService service;

  @Tool(name = "createTicket", description = "Create the Support Ticket")
  String createTicket(@ToolParam(description = "Details to create a Support ticket")
  TicketRequest ticketRequest, ToolContext toolContext) {
    String username = (String) toolContext.getContext().get("username");
    logger.info("Creating support ticket for user: {} with details: {}", username, ticketRequest);
    HelpDeskTicket savedTicket = service.createTicket(ticketRequest, username);
    logger.info("Ticket created successfully. Ticket ID: {}, Username: {}", savedTicket.getId(),
        savedTicket.getUsername());
    return "Ticket #" + savedTicket.getId() + " created successfully for the user "
        + savedTicket.getUsername();
  }

  @Tool(description = "Fetch the status of the open tickets based on a given username")
  List<HelpDeskTicket> getTicketStatus(ToolContext toolContext) {
    String username = (String) toolContext.getContext().get("username");
    logger.info("Fetching tickets for user: {}", username);
    List<HelpDeskTicket> tickets = service.getTicketsByUsername(username);
    logger.info("Found {} tickets for user: {}", tickets.size(), username);
    return tickets;
  }
}
