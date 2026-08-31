package com.eazybytes.mcpclient.util;

import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpProgress;
import org.springframework.stereotype.Component;

@Component
public class HelpDeskToolProgressListener {

  private static final Logger logger = LoggerFactory.getLogger(HelpDeskToolProgressListener.class);

  @McpProgress(clients = "eazybytes")
  public void onProgress(McpSchema.ProgressNotification progressNotification) {
    logger.info("Progress update - {}% complete received for Request ID {}: Message: {}",
        progressNotification.progress(), progressNotification.progressToken(),
        progressNotification.message());
  }
}
