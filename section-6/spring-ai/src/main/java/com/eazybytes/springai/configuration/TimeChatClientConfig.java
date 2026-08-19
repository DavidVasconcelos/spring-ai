package com.eazybytes.springai.configuration;

import com.eazybytes.springai.advisor.TokenUsageAuditAdvisor;
import com.eazybytes.springai.tools.TimeTools;
import org.springframework.ai.chat.cache.semantic.SemanticCacheAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class TimeChatClientConfig {

  @Bean("timeChatClient")
  public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory,
       SemanticCacheAdvisor semanticCacheAdvisor, TimeTools timeTools) {
    SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE);
    TokenUsageAuditAdvisor tokenUsageAuditAdvisor = new TokenUsageAuditAdvisor();
    MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
        .build();

    return chatClientBuilder
        .defaultTools(timeTools)
        .defaultAdvisors(
            memoryAdvisor,
            tokenUsageAuditAdvisor,
            semanticCacheAdvisor,
            loggerAdvisor
        )
        .build();
  }
}
