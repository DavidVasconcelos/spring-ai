package com.eazybytes.springai.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

  @Bean
  public ChatClient.Builder openAiChatBuilder(OpenAiChatModel openAiChatModel) {
    return ChatClient.builder(openAiChatModel);
  }

  @Bean
  public ChatClient.Builder ollamaChatBuilder(OllamaChatModel ollamaChatModel) {
    return ChatClient.builder(ollamaChatModel);
  }
}