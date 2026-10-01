package com.eazybytes.mcpclient.util;

import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.ElicitResult.Action;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpElicitation;
import org.springframework.stereotype.Component;

@Component
public class HelpDeskElicitationProvider {

  private static final Logger logger = LoggerFactory.getLogger(HelpDeskElicitationProvider.class);

  @McpElicitation(clients = "eazybytes")
  public McpSchema.ElicitResult handleElicitationRequest(McpSchema.ElicitRequest request) {
    logger.info("Received MCP elicitation request froms server: {}", request);

    // Simulate a human filling in the requested form. The keys here must match the
    // field names of the server's requested schema (TicketContactInfo: priority, contactPhone).
    Map<String, Object> userResponse = Map.of(
        "priority", "HIGH",
        "contactPhone", "+1-202-555-0185"
    );

    logger.info("Responding to elicitation with ACCEPT and data: {}", userResponse);
    return McpSchema.ElicitResult.builder(Action.ACCEPT)
        .content(userResponse)
        .build();
  }
}
