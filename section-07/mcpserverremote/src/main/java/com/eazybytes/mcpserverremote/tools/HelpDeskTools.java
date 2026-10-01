package com.eazybytes.mcpserverremote.tools;

import com.eazybytes.mcpserverremote.entity.HelpDeskTicket;
import com.eazybytes.mcpserverremote.model.TicketContactInfo;
import com.eazybytes.mcpserverremote.model.TicketRequest;
import com.eazybytes.mcpserverremote.service.HelpDeskTicketService;
import io.modelcontextprotocol.spec.McpSchema;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.ai.mcp.annotation.context.McpSyncRequestContext;
import org.springframework.ai.mcp.annotation.context.StructuredElicitResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpDeskTools {

  private static final Logger logger = LoggerFactory.getLogger(HelpDeskTools.class);

  private final HelpDeskTicketService service;
  private static final String DEFAULT_PRIORITY = "MEDIUM";
  private static final String NO_PHONE_PROVIDED = "N/A";

  @McpTool(name = "createTicket", description = "Create the Support Ticket")
  String createTicket(@McpToolParam(description = "Details to create a Support ticket")
  TicketRequest ticketRequest, McpSyncRequestContext context) {
    logger.info("Creating support ticket for user: {} with details: {}", ticketRequest.username(),
        ticketRequest.issue());

    String priority = DEFAULT_PRIORITY;
    String contactPhone = NO_PHONE_PROVIDED;

    if ((context.elicitEnabled())) {
      TicketContactInfo info = elicit(context, priority, contactPhone);
      priority = info.priority();
      contactPhone = info.contactPhone();
    } else {
      logger.warn(
          "Connected MCP client does not support elicitation. Using default ticket details.");
    }

    HelpDeskTicket savedTicket = service.createTicket(ticketRequest, priority, contactPhone);
    logger.info("Ticket created successfully. Ticket ID: {}, Username: {}, Priority: {}",
        savedTicket.getId(), savedTicket.getUsername(), savedTicket.getPriority());

    return "Ticket #" + savedTicket.getId() + " created successfully for the user "
        + savedTicket.getUsername() + "with priority: " + savedTicket.getPriority() + " (contact"
        + "phone: " + savedTicket.getContactPhone() + ").";
  }


  @McpTool(name = "summarizeTickets", description = "Generate a friendly, natural-language summary "
      + "of all support tickets that belong to a given username")
  String summarizeTickets(
      @McpToolParam(description = "Username to summarize the help desk tickets for")
      String username, McpSyncRequestContext context) {
    logger.info("Generating ticket summary for user: {}", username);
    List<HelpDeskTicket> tickets = service.getTicketsByUsername(username);

    if (tickets.isEmpty()) {
      return "No support tickets were found for user: " + username;
    }

    // MCP Sampling lets this server ask the connected client to run an LLM
    // completion on its behalf. First make sure the client actually advertised
    // the sampling capability during initialization.
    if (!context.sampleEnabled()) {
      logger.warn(
          "Connected MCP client does not support sampling. Returning raw ticket dat instead");
    }

    String ticketData = tickets.stream()
        .map(ticket -> "Ticket #" + ticket.getId() + " | Issue: " + ticket.getIssue()
            + " | Status: " + ticket.getStatus() + " | ETA: " + ticket.getEta())
        .collect(Collectors.joining("\n"));

    String systemPrompt = """
        You are a friendly help desk assistant. Using ONLY the ticket data provided by the user,
        write a short, warm summary for the customer about the status of their support tickets.
        Mention how many tickets they have in total, group them by status (OPEN, IN_PROGRESS, CLOSED),
        and reassure them about the ones that are still being worked on. Keep it under 120 words and
        do not invent any information that is not present in the ticket data.
        """;

    logger.info("Requesting LLM completion from the MCP client via sampling...");
    context.info("Asking your AI assistant to summarize " + tickets.size() + " ticket(s) for user: "
        + username);

    McpSchema.CreateMessageResult result = context.sample(spec -> spec
        .systemPrompt(systemPrompt)
        .message("Here are the support tickets for user: " + username + ": \n" + ticketData));

    String summary = ((McpSchema.TextContent) result.content()).text();
    logger.info("Sampling response received. Model used by client: {}", result.model());
    return summary;
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

    for (int i = 0; i < 10; i++) {
      int percent = (i * 100) / 10;
      context.progress(spec -> spec.progress(percent)
          .message("Fetching tickets for user: " + username + " - " + percent + "% complete"));
    }

    return tickets;
  }

  private TicketContactInfo elicit(McpSyncRequestContext context, String priority,
      String contactPhone) {
    context.info("Asking you for a few extra details before opening this ticket...");
    logger.info("Requesting additional ticket details from the MCP client via elicitation...");

    // The TicketContactInfo record is turned into the JSON 'requestedSchema' the
    // client should fill in. Spring AI maps the client's answer back into the record.
    StructuredElicitResult<TicketContactInfo> elicitResult = context.elicit(
        spec -> spec.message("Before we open your support ticket, please choose a "
            + "priority (LOW, MEDIUM, HIGH or URGENT) and share a contact phone number so our "
            + "team can reach you."),
        TicketContactInfo.class);
    logger.info("Elicitation finished with action: {}", elicitResult.action());

    return Objects.requireNonNullElseGet(elicitResult.structuredContent(), () -> {
      context.info("No extra details provided. Opening the ticket with default priority '"
          + DEFAULT_PRIORITY + "'.");
      return new TicketContactInfo(priority, contactPhone);
    });
  }
}
