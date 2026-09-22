package com.eazybytes.springai.advisor;

import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.model.ChatResponse;

public class TokenUsageAuditAdvisor implements CallAdvisor {

  private static final Logger logger = LoggerFactory.getLogger(TokenUsageAuditAdvisor.class);

  @Override
  public @NonNull ChatClientResponse adviseCall(@NonNull ChatClientRequest chatClientRequest,
      CallAdvisorChain callAdvisorChain) {
    ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

    Optional.ofNullable(chatClientResponse.chatResponse())
        .map(ChatResponse::getMetadata)
        .map(ChatResponseMetadata::getUsage)
        .ifPresent(usage -> logger.info("Token usage audit: {}", usage));

    return chatClientResponse;
  }

  @Override
  public @NonNull String getName() {
    return "TokenUsageAuditAdvisor";
  }

  @Override
  public int getOrder() {
    return 1;
  }
}
