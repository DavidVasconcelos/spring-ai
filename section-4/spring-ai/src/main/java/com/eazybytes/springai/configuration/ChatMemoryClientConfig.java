package com.eazybytes.springai.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryClientConfig {

  @Bean("chatMemoryChatClient")
  public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
    SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
    MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
        .build();

    return chatClientBuilder
        .defaultAdvisors(loggerAdvisor, memoryAdvisor)
        .build();
  }
}
