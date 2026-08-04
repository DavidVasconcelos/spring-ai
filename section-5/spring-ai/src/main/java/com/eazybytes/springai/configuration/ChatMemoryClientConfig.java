package com.eazybytes.springai.configuration;

import com.eazybytes.springai.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class ChatMemoryClientConfig {

  @Bean
  public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
    return MessageWindowChatMemory.builder()
        .maxMessages(10)
        .chatMemoryRepository(jdbcChatMemoryRepository)
        .build();
  }

  @Bean
  RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore) {
    return RetrievalAugmentationAdvisor.builder()
        .documentRetriever(
            VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .topK(3)
                .similarityThreshold(0.5)
                .build()
        )
        .build();
  }

  @Bean("chatMemoryChatClient")
  public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory,
      RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {
    SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE);
    TokenUsageAuditAdvisor tokenUsageAuditAdvisor = new TokenUsageAuditAdvisor();
    MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
        .build();

    return chatClientBuilder
        .defaultAdvisors(
            memoryAdvisor,
            retrievalAugmentationAdvisor,
            tokenUsageAuditAdvisor,
            loggerAdvisor
        )
        .build();
  }
}
