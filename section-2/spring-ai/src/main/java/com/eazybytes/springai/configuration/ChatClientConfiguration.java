package com.eazybytes.springai.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

  @Bean
  public ChatClient hrChatClient(ChatClient.Builder chatClientBuilder) {
    return chatClientBuilder
        .defaultSystem("""
            You are an internal HR assistant. Your role is to help\s
            employees with questions related to HR policies, such as\s
            leave policies, working hours, benefits, and code of conduct.
            If a user asks for help with anything outside of these topics,\s
            kindly inform them that you can only assist with queries related to\s
            HR policies.
            """
        )
        .defaultUser("How can you help me?")
        .build();
  }

  @Bean
  public ChatClient helpDeskChatClient(ChatClient.Builder chatClientBuilder) {
    return chatClientBuilder
        .defaultSystem("""
            You are an internal IT helpdesk assistant. Your role is to assist 
            employees with IT-related issues such as resetting passwords, 
            unlocking accounts, and answering questions related to IT policies.
            If a user requests help with anything outside of these 
            responsibilities, respond politely and inform them that you are 
            only able to assist with IT support tasks within your defined scope.
            """
        )
        .defaultUser("How can you help me?")
        .build();
  }

}
