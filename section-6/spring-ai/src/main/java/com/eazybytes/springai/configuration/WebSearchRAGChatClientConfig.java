package com.eazybytes.springai.configuration;

import com.eazybytes.springai.advisor.TokenUsageAuditAdvisor;
import com.eazybytes.springai.rag.WebSearchDocumentRetriever;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestClient;

@Configuration
class WebSearchRAGChatClientConfig {

  @Bean("webSearchRAGChatClient")
  ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory,
      RestClient.Builder restClientBuilder) {
    Advisor loggerAdvisor = new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE);
    Advisor tokenUsageAuditAdvisor = new TokenUsageAuditAdvisor();
    Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
    Advisor webSearchRAGAdvisor = RetrievalAugmentationAdvisor.builder()
        .documentRetriever(WebSearchDocumentRetriever.builder()
            .restClientBuilder(restClientBuilder)
            .maxResults(5)
            .build())
        .build();

    return chatClientBuilder
        .defaultAdvisors(
            List.of(loggerAdvisor, memoryAdvisor, tokenUsageAuditAdvisor, webSearchRAGAdvisor))
        .build();
  }
}
