package com.eazybytes.springai.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
class HelpDeskTicketChatClientConfig {

  @Value("classpath:promptTemplates/helpDeskSystemPromptTemplate.st")
  Resource helpDeskSystemPromptTemplate;

  @Bean("helpDeskTicketChatClient")
  ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
    MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
        .build();

    return chatClientBuilder
        .defaultSystem(helpDeskSystemPromptTemplate)
        .defaultAdvisors(memoryAdvisor)
        .build();
  }
}
