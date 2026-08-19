package com.eazybytes.springai.configuration;

import com.eazybytes.springai.advisor.TokenUsageAuditAdvisor;
import java.util.List;
import org.springframework.ai.chat.cache.semantic.SemanticCacheAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
class OpenChatClientConfig {

  @Bean("openChatClient")
  ChatClient openChatClient(ChatClient.Builder chatClientBuilder,
      SemanticCacheAdvisor semanticCacheAdvisor) {
    return chatClientBuilder
        .defaultAdvisors(List.of(
            new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE),
            new TokenUsageAuditAdvisor(),
            semanticCacheAdvisor
        ))
        .build();
  }
}
