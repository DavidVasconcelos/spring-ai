package com.eazybytes.multimodel.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

  @Bean
  public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel) {
    return ChatClient.create(openAiChatModel);
  }

  @Bean
  public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel) {
    return ChatClient.builder(ollamaChatModel).build();
  }
}
