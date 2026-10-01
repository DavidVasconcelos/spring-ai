package com.eazybytes.springai.configuration;

import org.springframework.ai.chat.cache.semantic.SemanticCacheAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenChatClientConfig {

  @Bean("openChatClient")
  ChatClient openChatClient(ChatClient.Builder chatClientBuilder,
      SemanticCacheAdvisor semanticCacheAdvisor) {
    return chatClientBuilder
        .defaultAdvisors(semanticCacheAdvisor)
        .build();
  }
}
