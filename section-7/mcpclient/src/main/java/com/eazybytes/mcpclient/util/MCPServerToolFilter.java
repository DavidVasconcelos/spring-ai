package com.eazybytes.mcpclient.util;

import io.modelcontextprotocol.spec.McpSchema.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.McpConnectionInfo;
import org.springframework.ai.mcp.McpToolFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MCPServerToolFilter implements McpToolFilter {

  private static final Logger logger = LoggerFactory.getLogger(MCPServerToolFilter.class);

  @Override
  public boolean test(McpConnectionInfo mcpConnectionInfo, Tool tool) {
    Assert.notNull(mcpConnectionInfo.initializeResult(), "server must not be null");
    String serverName = mcpConnectionInfo.initializeResult()
        .serverInfo()
        .name();
    String toolName = tool.name();

    logger.info("Evaluating tool '{}' from MCP server '{}'", toolName, serverName);

    // Example: Block all tools from GitHub MCP server.
    // TIP: instead of the hard-coded "github", inject a configurable list, e.g.
    //   @Value("${mcp.tool-filter.blocked-servers:}") List<String> blockedServers;
    // so prod can block it while dev allows it (see class-level docs).
    if (serverName.contains("github")) {
      logger.warn("Tool '{}' rejected because it belongs to blocked MCP server '{}'", toolName,
          serverName);
      return false;
    }

    // Example: Block any "write"-style tool to keep this client read-only.
    // TIP: drive the prefixes from config, e.g.
    //   mcp.tool-filter.blocked-tool-prefixes=write_,delete_
    // and load them per profile (strict in prod, relaxed in dev).
    if (toolName.contains("write_")) {
      logger.warn("Tool '{}' rejected because it belongs to the blocked list '{}'", toolName,
          serverName);
      return false;
    }

    logger.info("Tool '{}' approved from MCP server {}", toolName, serverName);
    return true;
  }
}
