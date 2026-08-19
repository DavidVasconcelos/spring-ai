package com.eazybytes.springai.configuration;

import com.eazybytes.springai.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;

@Configuration
class HelpDeskTicketChatClientConfig {

  @Value("classpath:promptTemplates/helpDeskSystemPromptTemplate.st")
  Resource helpDeskSystemPromptTemplate;

  @Bean("helpDeskTicketChatClient")
  ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
    SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE);
    TokenUsageAuditAdvisor tokenUsageAuditAdvisor = new TokenUsageAuditAdvisor();
    MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
        .build();

    return chatClientBuilder
        .defaultSystem(helpDeskSystemPromptTemplate)
        .defaultAdvisors(
            memoryAdvisor,
            tokenUsageAuditAdvisor,
            loggerAdvisor
        )
        .build();
  }
}
