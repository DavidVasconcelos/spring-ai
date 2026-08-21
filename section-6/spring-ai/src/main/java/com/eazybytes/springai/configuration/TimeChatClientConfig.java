package com.eazybytes.springai.configuration;

import com.eazybytes.springai.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TimeChatClientConfig {

  @Bean("timeChatClient")
  ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory,
      TimeTools timeTools) {
    MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
        .build();

    return chatClientBuilder
        .defaultTools(timeTools)
        .defaultAdvisors(memoryAdvisor)
        .build();
  }
}
